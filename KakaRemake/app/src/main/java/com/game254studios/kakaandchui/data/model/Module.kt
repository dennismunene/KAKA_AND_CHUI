package com.game254studios.kakaandchui.data.model

enum class Module(
    val displayName: String,
    val swahiliName: String,
    val iconAsset: String
) {
    VOKALI("Vowels", "Vokali", "gfx/mainmenu/vokali.png"),
    TARAKIMU("Numbers", "Tarakimu", "gfx/mainmenu/tarakimu.png"),
    MAUMBO("Shapes", "Maumbo", "gfx/mainmenu/maumbo.png"),
    RANGI("Colors", "Rangi", "gfx/mainmenu/rangi.png")
}
