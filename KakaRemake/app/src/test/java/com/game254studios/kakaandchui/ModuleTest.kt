package com.game254studios.kakaandchui

import com.game254studios.kakaandchui.data.model.Module
import org.junit.Assert.*
import org.junit.Test

class ModuleTest {

    @Test
    fun `Module has at least 4 entries`() {
        assertTrue(Module.entries.size >= 4)
    }

    @Test
    fun `each module has non-blank displayName`() {
        for (module in Module.entries) {
            assertTrue("displayName blank for $module", module.displayName.isNotBlank())
        }
    }

    @Test
    fun `each module has non-blank swahiliName`() {
        for (module in Module.entries) {
            assertTrue("swahiliName blank for $module", module.swahiliName.isNotBlank())
        }
    }

    @Test
    fun `each module has non-blank iconAsset`() {
        for (module in Module.entries) {
            assertTrue("iconAsset blank for $module", module.iconAsset.isNotBlank())
        }
    }

    @Test
    fun `VOKALI displayName is Vowels`() {
        assertEquals("Vowels", Module.VOKALI.displayName)
    }

    @Test
    fun `VOKALI swahiliName is Vokali`() {
        assertEquals("Vokali", Module.VOKALI.swahiliName)
    }
}
