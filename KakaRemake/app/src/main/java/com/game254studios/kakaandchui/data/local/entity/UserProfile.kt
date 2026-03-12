package com.game254studios.kakaandchui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val avatarIndex: Int = 0,
    val xp: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
