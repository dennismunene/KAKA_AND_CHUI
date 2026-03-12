package com.game254studios.kakaandchui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_streaks")
data class DailyStreak(
    @PrimaryKey val profileId: Int,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: String = "",
    val totalDaysActive: Int = 0
)
