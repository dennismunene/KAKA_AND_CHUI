package com.game254studios.kakaandchui

import com.game254studios.kakaandchui.data.model.ALL_ACHIEVEMENTS
import org.junit.Assert.*
import org.junit.Test

class AchievementDefTest {

    @Test
    fun `ALL_ACHIEVEMENTS contains 8 items`() {
        assertEquals(8, ALL_ACHIEVEMENTS.size)
    }

    @Test
    fun `each achievement has unique id`() {
        val ids = ALL_ACHIEVEMENTS.map { it.id }
        assertEquals(ids.size, ids.distinct().size)
    }

    @Test
    fun `each achievement has non-blank name`() {
        for (a in ALL_ACHIEVEMENTS) {
            assertTrue("name blank for ${a.id}", a.name.isNotBlank())
        }
    }

    @Test
    fun `each achievement has non-blank description`() {
        for (a in ALL_ACHIEVEMENTS) {
            assertTrue("description blank for ${a.id}", a.description.isNotBlank())
        }
    }

    @Test
    fun `each achievement has non-blank icon`() {
        for (a in ALL_ACHIEVEMENTS) {
            assertTrue("icon blank for ${a.id}", a.icon.isNotBlank())
        }
    }
}
