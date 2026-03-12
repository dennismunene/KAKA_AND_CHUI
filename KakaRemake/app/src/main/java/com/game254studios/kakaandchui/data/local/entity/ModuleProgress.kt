package com.game254studios.kakaandchui.data.local.entity

import androidx.room.Entity

@Entity(tableName = "module_progress", primaryKeys = ["profileId", "moduleId"])
data class ModuleProgress(
    val profileId: Int,
    val moduleId: String,
    val lessonsCompleted: Int = 0,
    val quizBestScore: Int = 0,
    val quizBestTotal: Int = 0,
    val quizStars: Int = 0,
    val timesPlayed: Int = 0,
    val lastPlayedAt: Long = 0
)
