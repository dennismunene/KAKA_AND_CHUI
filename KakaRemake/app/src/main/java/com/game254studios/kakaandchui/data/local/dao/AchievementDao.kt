package com.game254studios.kakaandchui.data.local.dao

import androidx.room.*
import com.game254studios.kakaandchui.data.local.entity.Achievement
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements WHERE profileId = :profileId")
    fun getAchievements(profileId: Int): Flow<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE profileId = :profileId AND isUnlocked = 1")
    fun getUnlockedAchievements(profileId: Int): Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAchievement(achievement: Achievement)
}
