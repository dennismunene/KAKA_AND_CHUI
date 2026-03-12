package com.game254studios.kakaandchui.data.local.entity

import androidx.room.Entity

@Entity(tableName = "achievements", primaryKeys = ["profileId", "achievementId"])
data class Achievement(
    val profileId: Int,
    val achievementId: String,
    val earnedAt: Long = 0,
    val isUnlocked: Boolean = false
)
