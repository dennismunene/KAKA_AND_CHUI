package com.game254studios.kakaandchui.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.game254studios.kakaandchui.data.local.dao.*
import com.game254studios.kakaandchui.data.local.entity.*

@Database(
    entities = [UserProfile::class, ModuleProgress::class, Achievement::class, DailyStreak::class, CoinBalance::class],
    version = 2,
    exportSchema = false
)
abstract class KakaDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun progressDao(): ProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun streakDao(): StreakDao
    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile private var INSTANCE: KakaDatabase? = null

        fun getInstance(context: Context): KakaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KakaDatabase::class.java,
                    "kaka_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
