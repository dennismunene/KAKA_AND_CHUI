package com.game254studios.kakaandchui

import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.data.repository.ContentRepository
import org.junit.Assert.*
import org.junit.Test

class ContentRepositoryTest {

    @Test
    fun `VOKALI returns 5 vowels`() {
        val items = ContentRepository.getItems(Module.VOKALI)
        assertEquals(5, items.size)
        assertEquals(listOf("a", "e", "i", "o", "u"), items.map { it.id })
    }

    @Test
    fun `TARAKIMU returns 10 numbers`() {
        val items = ContentRepository.getItems(Module.TARAKIMU)
        assertEquals(10, items.size)
        val expectedIds = (1..10).map { "t$it" }
        assertEquals(expectedIds, items.map { it.id })
    }

    @Test
    fun `MAUMBO returns 6 shapes`() {
        val items = ContentRepository.getItems(Module.MAUMBO)
        assertEquals(6, items.size)
    }

    @Test
    fun `RANGI returns 6 colors`() {
        val items = ContentRepository.getItems(Module.RANGI)
        assertEquals(6, items.size)
    }

    @Test
    fun `original 4 modules items have non-blank fields`() {
        val originalModules = listOf(Module.VOKALI, Module.TARAKIMU, Module.MAUMBO, Module.RANGI)
        for (module in originalModules) {
            val items = ContentRepository.getItems(module)
            for (item in items) {
                assertTrue("id blank in $module", item.id.isNotBlank())
                assertTrue("name blank in $module", item.name.isNotBlank())
                assertTrue("imageAsset blank in $module", item.imageAsset.isNotBlank())
                assertTrue("audioAsset blank in $module", item.audioAsset.isNotBlank())
                assertTrue("quizAudioAsset blank in $module", item.quizAudioAsset.isNotBlank())
            }
        }
    }

    @Test
    fun `no duplicate IDs within original modules`() {
        val originalModules = listOf(Module.VOKALI, Module.TARAKIMU, Module.MAUMBO, Module.RANGI)
        for (module in originalModules) {
            val items = ContentRepository.getItems(module)
            val ids = items.map { it.id }
            assertEquals("Duplicate IDs in $module", ids.size, ids.distinct().size)
        }
    }
}
