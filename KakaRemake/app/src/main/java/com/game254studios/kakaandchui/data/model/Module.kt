package com.game254studios.kakaandchui.data.model

enum class Module(
    val displayName: String,
    val swahiliName: String,
    val iconAsset: String,
    val emoji: String,
    val colorHex: Long
) {
    VOKALI("Vowels", "Vokali", "gfx/mainmenu/vokali.png", "🔤", 0xFFFF9800),
    VOKALI_MANENO("Words", "Maneno", "gfx/mainmenu/vokali.png", "📖", 0xFF4CAF50),
    TARAKIMU("Numbers 1-10", "Tarakimu", "gfx/mainmenu/tarakimu.png", "🔢", 0xFF03A9F4),
    TARAKIMU_11_20("Numbers 11-20", "Tarakimu 11-20", "gfx/mainmenu/tarakimu.png", "🔟", 0xFFFFB300),
    MAUMBO("Shapes", "Maumbo", "gfx/mainmenu/maumbo.png", "🔺", 0xFFE91E63),
    RANGI("Colors", "Rangi", "gfx/mainmenu/rangi.png", "🎨", 0xFF9C27B0)
}
