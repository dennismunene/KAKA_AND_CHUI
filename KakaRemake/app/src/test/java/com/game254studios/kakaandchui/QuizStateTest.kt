package com.game254studios.kakaandchui

import com.game254studios.kakaandchui.viewmodel.QuizState
import org.junit.Assert.*
import org.junit.Test

class QuizStateTest {

    @Test
    fun `default score is 0`() {
        assertEquals(0, QuizState().score)
    }

    @Test
    fun `default isFinished is false`() {
        assertFalse(QuizState().isFinished)
    }

    @Test
    fun `default options is empty`() {
        assertTrue(QuizState().options.isEmpty())
    }

    @Test
    fun `default currentIndex is 0`() {
        assertEquals(0, QuizState().currentIndex)
    }

    @Test
    fun `default answered is false`() {
        assertFalse(QuizState().answered)
    }

    @Test
    fun `default selectedAnswer is null`() {
        assertNull(QuizState().selectedAnswer)
    }

    @Test
    fun `default currentItem is null`() {
        assertNull(QuizState().currentItem)
    }

    @Test
    fun `default xpEarned is 0`() {
        assertEquals(0, QuizState().xpEarned)
    }

    @Test
    fun `default newAchievements is empty`() {
        assertTrue(QuizState().newAchievements.isEmpty())
    }
}
