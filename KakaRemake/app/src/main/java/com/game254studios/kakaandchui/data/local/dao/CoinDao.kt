package com.game254studios.kakaandchui.data.local.dao

import androidx.room.*
import com.game254studios.kakaandchui.data.local.entity.CoinBalance
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin_balances WHERE profileId = :profileId")
    suspend fun getBalance(profileId: Int): CoinBalance?

    @Query("SELECT coins FROM coin_balances WHERE profileId = :profileId")
    fun observeCoins(profileId: Int): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBalance(balance: CoinBalance)

    @Query("UPDATE coin_balances SET coins = coins + :amount, totalEarned = totalEarned + :amount WHERE profileId = :profileId")
    suspend fun addCoins(profileId: Int, amount: Int)

    @Query("UPDATE coin_balances SET coins = coins - :amount, totalSpent = totalSpent + :amount WHERE profileId = :profileId AND coins >= :amount")
    suspend fun spendCoins(profileId: Int, amount: Int): Int
}
