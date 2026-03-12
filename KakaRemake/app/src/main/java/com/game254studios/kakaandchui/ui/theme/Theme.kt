package com.game254studios.kakaandchui.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val KakaColorScheme = lightColorScheme(
    primary = KakaOrange,
    onPrimary = KakaWhite,
    primaryContainer = KakaOrangeLight,
    secondary = KakaGreen,
    onSecondary = KakaWhite,
    secondaryContainer = KakaGreenLight,
    tertiary = KakaSkyBlue,
    onTertiary = KakaWhite,
    tertiaryContainer = KakaSkyBlueLight,
    background = KakaCream,
    onBackground = KakaDark,
    surface = KakaWhite,
    onSurface = KakaDark,
)

@Composable
fun KakaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KakaColorScheme,
        typography = KakaTypography,
        shapes = KakaShapes,
        content = content
    )
}
