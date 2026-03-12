package com.game254studios.kakaandchui.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game254studios.kakaandchui.ui.components.SpriteAnimation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    var startAnim by remember { mutableStateOf(false) }

    val titleAlpha by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = tween(800),
        label = "title_alpha"
    )
    val titleScale by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0.5f,
        animationSpec = tween(800, easing = EaseOutBack),
        label = "title_scale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val bounce1 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -12f,
        animationSpec = infiniteRepeatable(
            tween(600, easing = EaseInOutSine), RepeatMode.Reverse
        ), label = "b1"
    )
    val bounce2 by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -12f,
        animationSpec = infiniteRepeatable(
            tween(600, 150, easing = EaseInOutSine), RepeatMode.Reverse
        ), label = "b2"
    )

    LaunchedEffect(Unit) {
        startAnim = true
        delay(2500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Animated loading character (3 cols × 2 rows sprite sheet)
            SpriteAnimation(
                assetPath = "gfx/loading_anim.png",
                columns = 3, rows = 2, frameCount = 6,
                frameDurationMs = 150L,
                size = 100.dp,
                modifier = Modifier.alpha(titleAlpha)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🦜",
                    fontSize = 48.sp,
                    modifier = Modifier.offset(y = bounce1.dp).alpha(titleAlpha)
                )
                Text(
                    text = "Kaka & Chui",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alpha(titleAlpha).scale(titleScale)
                )
                Text(
                    text = "🐆",
                    fontSize = 48.sp,
                    modifier = Modifier.offset(y = bounce2.dp).alpha(titleAlpha)
                )
            }
        }
    }
}
