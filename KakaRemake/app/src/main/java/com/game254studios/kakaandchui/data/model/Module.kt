package com.game254studios.kakaandchui.data.model

enum class Module(
    val displayName: String,
    val swahiliName: String,
    val iconAsset: String
) {
    VOKALI("Vowels", "Vokali", "gfx/mainmenu/vokali.png"),
    VOKALI_MANENO("Words", "Maneno", "gfx/mainmenu/vokali.png"),
    TARAKIMU("Numbers 1-10", "Tarakimu", "gfx/mainmenu/tarakimu.png"),
    TARAKIMU_11_20("Numbers 11-20", "Tarakimu 11-20", "gfx/mainmenu/tarakimu.png"),
    MAUMBO("Shapes", "Maumbo", "gfx/mainmenu/maumbo.png"),
    RANGI("Colors", "Rangi", "gfx/mainmenu/rangi.png")
}
