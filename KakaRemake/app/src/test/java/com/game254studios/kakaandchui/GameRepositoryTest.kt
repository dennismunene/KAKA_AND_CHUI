package com.game254studios.kakaandchui

import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.repository.GameRepository
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private lateinit var repo: GameRepository

    @Before
    fun setUp() {
        val db = mockk<KakaDatabase>(relaxed = true)
        val prefs = mockk<UserPreferences>(relaxed = true)
        repo = GameRepository(db, prefs)
    }

    // Level calculations

    @Test
    fun `getLevel 0 xp returns level 1`() {
        assertEquals(1, repo.getLevel(0))
    }

    @Test
    fun `getLevel 99 xp returns level 1`() {
        assertEquals(1, repo.getLevel(99))
    }

    @Test
    fun `getLevel 100 xp returns level 2`() {
        assertEquals(2, repo.getLevel(100))
    }

    @Test
    fun `getLevel 999 xp returns level 10`() {
        assertEquals(10, repo.getLevel(999))
    }

    // Level names

    @Test
    fun `getLevelName 1 returns Chick`() {
        assertEquals("Chick", repo.getLevelName(1))
    }

    @Test
    fun `getLevelName 3 returns Duckling`() {
        assertEquals("Duckling", repo.getLevelName(3))
    }

    @Test
    fun `getLevelName 5 returns Parrot`() {
        assertEquals("Parrot", repo.getLevelName(5))
    }

    @Test
    fun `getLevelName 10 returns Eagle`() {
        assertEquals("Eagle", repo.getLevelName(10))
    }

    // Star calculation (mirrors saveQuizResult logic)

    private fun calculateStars(score: Int, total: Int): Int {
        val percentage = if (total > 0) score.toFloat() / total else 0f
        return when {
            percentage >= 0.9f -> 3
            percentage >= 0.6f -> 2
            percentage > 0f -> 1
            else -> 0
        }
    }

    @Test
    fun `90 percent or above earns 3 stars`() {
        assertEquals(3, calculateStars(9, 10))
        assertEquals(3, calculateStars(10, 10))
    }

    @Test
    fun `60 to 89 percent earns 2 stars`() {
        assertEquals(2, calculateStars(6, 10))
        assertEquals(2, calculateStars(8, 10))
    }

    @Test
    fun `above 0 below 60 percent earns 1 star`() {
        assertEquals(1, calculateStars(1, 10))
        assertEquals(1, calculateStars(5, 10))
    }

    @Test
    fun `0 percent earns 0 stars`() {
        assertEquals(0, calculateStars(0, 10))
        assertEquals(0, calculateStars(0, 0))
    }
}
