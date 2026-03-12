package com.game254studios.kakaandchui.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.game254studios.kakaandchui.ui.theme.KakaGreen
import com.game254studios.kakaandchui.ui.theme.KakaOrange
import com.game254studios.kakaandchui.ui.theme.KakaSkyBlue
import com.game254studios.kakaandchui.ui.theme.StarGold
import kotlin.math.sin
import kotlin.random.Random

private data class ConfettiParticle(
    val xFraction: Float,
    val color: Color,
    val radius: Float,
    val speedFactor: Float,
    val driftAmplitude: Float
)

private val confettiColors = listOf(KakaOrange, KakaGreen, KakaSkyBlue, StarGold)

@Composable
fun ConfettiOverlay(isVisible: Boolean) {
    if (!isVisible) return

    val particles = remember {
        List(25) {
            ConfettiParticle(
                xFraction = Random.nextFloat(),
                color = confettiColors[Random.nextInt(confettiColors.size)],
                radius = Random.nextFloat() * 6f + 4f,
                speedFactor = Random.nextFloat() * 0.4f + 0.6f,
                driftAmplitude = Random.nextFloat() * 30f + 10f
            )
        }
    }

    val progress = remember { Animatable(0f) }

    LaunchedEffect(isVisible) {
        progress.snapTo(0f)
        progress.animateTo(1f, tween(1500))
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val y = size.height * (1f - progress.value * particle.speedFactor)
            val x = size.width * particle.xFraction +
                sin(progress.value * 8f * particle.speedFactor) * particle.driftAmplitude
            val alpha = (1f - progress.value).coerceIn(0f, 1f)
            drawCircle(
                color = particle.color.copy(alpha = alpha),
                radius = particle.radius,
                center = Offset(x, y)
            )
        }
    }
}
