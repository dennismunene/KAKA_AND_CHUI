package com.game254studios.kakaandchui.data.local.dao

import androidx.room.*
import com.game254studios.kakaandchui.data.local.entity.ModuleProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM module_progress WHERE profileId = :profileId")
    fun getProgressForProfile(profileId: Int): Flow<List<ModuleProgress>>

    @Query("SELECT * FROM module_progress WHERE profileId = :profileId AND moduleId = :moduleId")
    suspend fun getModuleProgress(profileId: Int, moduleId: String): ModuleProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: ModuleProgress)

    @Query("UPDATE module_progress SET quizBestScore = :score, quizBestTotal = :total, quizStars = :stars, timesPlayed = timesPlayed + 1, lastPlayedAt = :timestamp WHERE profileId = :profileId AND moduleId = :moduleId")
    suspend fun updateQuizScore(profileId: Int, moduleId: String, score: Int, total: Int, stars: Int, timestamp: Long)
}
