package com.game254studios.kakaandchui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_balances")
data class CoinBalance(
    @PrimaryKey val profileId: Int,
    val coins: Int = 0,
    val totalEarned: Int = 0,
    val totalSpent: Int = 0
)
