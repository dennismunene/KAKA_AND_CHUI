package com.game254studios.kakaandchui.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlin.math.sin

/**
 * Animated bee that flies across the screen in a sine-wave path.
 * Uses the Bee.png sprite sheet (3 frames, horizontal strip).
 */
@Composable
fun FlyingBee(modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp
    val screenHeight = config.screenHeightDp

    val infiniteTransition = rememberInfiniteTransition(label = "bee_flight")

    // Horizontal position: fly across screen and back
    val xProgress by infiniteTransition.animateFloat(
        initialValue = -80f,
        targetValue = screenWidth.toFloat() + 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bee_x"
    )

    // Vertical sine-wave oscillation
    val yBase = screenHeight * 0.15f
    val yAmplitude = screenHeight * 0.08f
    val yOffset = yBase + (sin(xProgress * 0.03) * yAmplitude).toFloat()

    Box(modifier = modifier.fillMaxSize()) {
        SpriteAnimation(
            assetPath = "gfx/mainmenu/Bee.png",
            columns = 3,
            rows = 1,
            frameCount = 3,
            frameDurationMs = 100L,
            spriteWidth = 80.dp,
            spriteHeight = 50.dp,
            modifier = Modifier.offset(x = xProgress.dp, y = yOffset.dp)
        )
    }
}
