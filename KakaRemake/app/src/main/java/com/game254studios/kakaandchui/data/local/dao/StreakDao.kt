package com.game254studios.kakaandchui.data.local.dao

import androidx.room.*
import com.game254studios.kakaandchui.data.local.entity.DailyStreak

@Dao
interface StreakDao {
    @Query("SELECT * FROM daily_streaks WHERE profileId = :profileId")
    suspend fun getStreak(profileId: Int): DailyStreak?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStreak(streak: DailyStreak)
}
