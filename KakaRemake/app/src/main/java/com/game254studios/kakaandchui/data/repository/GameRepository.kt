package com.game254studios.kakaandchui.data.repository

import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.local.entity.*
import com.game254studios.kakaandchui.data.model.ALL_ACHIEVEMENTS
import com.game254studios.kakaandchui.data.model.AchievementDef
import com.game254studios.kakaandchui.data.model.Module
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class QuizReward(
    val xpEarned: Int,
    val coinsEarned: Int,
    val newAchievements: List<AchievementDef>
)

class GameRepository(private val db: KakaDatabase, private val prefs: UserPreferences) {

    private val profileDao = db.userProfileDao()
    private val progressDao = db.progressDao()
    private val achievementDao = db.achievementDao()
    private val streakDao = db.streakDao()
    private val coinDao = db.coinDao()

    // Profile management

    suspend fun getOrCreateDefaultProfile(): UserProfile {
        val activeId = prefs.activeProfileId.first()
        if (activeId > 0) {
            profileDao.getProfile(activeId)?.let { return it }
        }
        val profile = UserProfile(name = "Mchezaji", avatarIndex = 0)
        val newId = profileDao.insertProfile(profile).toInt()
        prefs.setActiveProfileId(newId)
        coinDao.upsertBalance(CoinBalance(profileId = newId))
        streakDao.upsertStreak(DailyStreak(profileId = newId))
        return profileDao.getProfile(newId)!!
    }

    suspend fun getActiveProfile(): UserProfile? {
        val activeId = prefs.activeProfileId.first()
        if (activeId <= 0) return null
        return profileDao.getProfile(activeId)
    }

    // XP & Levels

    suspend fun addXp(profileId: Int, amount: Int) {
        val profile = profileDao.getProfile(profileId) ?: return
        profileDao.updateProfile(profile.copy(xp = profile.xp + amount))
    }

    fun getLevel(xp: Int): Int = (xp / 100) + 1

    fun getLevelName(level: Int): String = when {
        level >= 10 -> "Eagle"
        level >= 5 -> "Parrot"
        level >= 3 -> "Duckling"
        else -> "Chick"
    }

    // Save quiz result and return rewards

    suspend fun saveQuizResult(profileId: Int, moduleId: String, score: Int, total: Int): QuizReward {
        val percentage = if (total > 0) score.toFloat() / total else 0f
        val stars = when {
            percentage >= 0.9f -> 3
            percentage >= 0.6f -> 2
            percentage > 0f -> 1
            else -> 0
        }

        // Update or create ModuleProgress
        val existing = progressDao.getModuleProgress(profileId, moduleId)
        if (existing != null) {
            val newBestScore = maxOf(existing.quizBestScore, score)
            val newBestStars = maxOf(existing.quizStars, stars)
            progressDao.upsertProgress(
                existing.copy(
                    quizBestScore = newBestScore,
                    quizBestTotal = total,
                    quizStars = newBestStars,
                    timesPlayed = existing.timesPlayed + 1,
                    lastPlayedAt = System.currentTimeMillis()
                )
            )
        } else {
            progressDao.upsertProgress(
                ModuleProgress(
                    profileId = profileId,
                    moduleId = moduleId,
                    quizBestScore = score,
                    quizBestTotal = total,
                    quizStars = stars,
                    timesPlayed = 1,
                    lastPlayedAt = System.currentTimeMillis()
                )
            )
        }

        // Award XP: 10 per correct answer, 50 bonus for 3 stars
        var xpEarned = score * 10
        if (stars == 3) xpEarned += 50
        addXp(profileId, xpEarned)

        // Award coins: 5 per correct, 25 bonus for 3 stars
        var coinsEarned = score * 5
        if (stars == 3) coinsEarned += 25
        addCoins(profileId, coinsEarned)

        // Update streak
        updateStreak(profileId)

        // Check achievements
        val newAchievements = checkAndAwardAchievements(profileId)

        return QuizReward(xpEarned, coinsEarned, newAchievements)
    }

    // Streak management

    suspend fun updateStreak(profileId: Int) {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val streak = streakDao.getStreak(profileId) ?: DailyStreak(profileId = profileId)

        if (streak.lastActiveDate == today) return // Already active today

        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        val newStreak = if (streak.lastActiveDate == yesterday) {
            streak.currentStreak + 1
        } else {
            1 // Reset streak
        }

        streakDao.upsertStreak(
            streak.copy(
                currentStreak = newStreak,
                longestStreak = maxOf(streak.longestStreak, newStreak),
                lastActiveDate = today,
                totalDaysActive = streak.totalDaysActive + 1
            )
        )
    }

    suspend fun getStreak(profileId: Int): DailyStreak {
        return streakDao.getStreak(profileId) ?: DailyStreak(profileId = profileId)
    }

    // Coins

    suspend fun getCoins(profileId: Int): Int {
        return coinDao.getBalance(profileId)?.coins ?: 0
    }

    suspend fun addCoins(profileId: Int, amount: Int) {
        val balance = coinDao.getBalance(profileId)
        if (balance != null) {
            coinDao.addCoins(profileId, amount)
        } else {
            coinDao.upsertBalance(CoinBalance(profileId = profileId, coins = amount, totalEarned = amount))
        }
    }

    suspend fun spendCoins(profileId: Int, amount: Int): Boolean {
        val balance = coinDao.getBalance(profileId) ?: return false
        if (balance.coins < amount) return false
        coinDao.spendCoins(profileId, amount)
        return true
    }

    // Achievements

    suspend fun checkAndAwardAchievements(profileId: Int): List<AchievementDef> {
        val unlocked = achievementDao.getUnlockedAchievements(profileId).first()
        val unlockedIds = unlocked.map { it.achievementId }.toSet()
        val newlyUnlocked = mutableListOf<AchievementDef>()

        val allProgress = progressDao.getProgressForProfile(profileId).first()
        val streak = streakDao.getStreak(profileId)

        for (def in ALL_ACHIEVEMENTS) {
            if (def.id in unlockedIds) continue

            val earned = when (def.id) {
                "first_steps" -> allProgress.any { it.timesPlayed > 0 }
                "vowel_master" -> allProgress.any { it.moduleId == Module.VOKALI.name && it.quizStars >= 3 }
                "word_wizard" -> allProgress.any { it.moduleId == Module.VOKALI_MANENO.name && it.quizStars >= 3 }
                "number_ninja" -> allProgress.any { it.moduleId == Module.TARAKIMU.name && it.quizStars >= 3 }
                "counting_champion" -> allProgress.any { it.moduleId == Module.TARAKIMU_11_20.name && it.quizStars >= 3 }
                "shape_shifter" -> allProgress.any { it.moduleId == Module.MAUMBO.name && it.quizStars >= 3 }
                "rainbow_warrior" -> allProgress.any { it.moduleId == Module.RANGI.name && it.quizStars >= 3 }
                "week_warrior" -> (streak?.currentStreak ?: 0) >= 7
                "consistent_learner" -> (streak?.currentStreak ?: 0) >= 30
                "kaka_best_friend" -> Module.entries.all { module ->
                    allProgress.any { it.moduleId == module.name && it.quizStars >= 1 }
                }
                else -> false
            }

            if (earned) {
                achievementDao.upsertAchievement(
                    Achievement(
                        profileId = profileId,
                        achievementId = def.id,
                        earnedAt = System.currentTimeMillis(),
                        isUnlocked = true
                    )
                )
                newlyUnlocked.add(def)
            }
        }

        return newlyUnlocked
    }

    fun getAchievementDefinitions(): List<AchievementDef> = ALL_ACHIEVEMENTS

    // Module stars for display

    suspend fun getModuleStars(profileId: Int): Map<String, Int> {
        val progress = progressDao.getProgressForProfile(profileId).first()
        return progress.associate { it.moduleId to it.quizStars }
    }
}
