package com.game254studios.kakaandchui.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

enum class AnswerState { DEFAULT, CORRECT, WRONG }

@Composable
fun QuizAnswerButton(
    text: String,
    state: AnswerState,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shakeOffset = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(1f) }

    LaunchedEffect(state) {
        when (state) {
            AnswerState.CORRECT -> {
                scaleAnim.animateTo(1.15f, tween(100))
                scaleAnim.animateTo(1f, tween(100))
            }
            AnswerState.WRONG -> {
                shakeOffset.animateTo(10f, tween(50))
                shakeOffset.animateTo(-10f, tween(50))
                shakeOffset.animateTo(10f, tween(50))
                shakeOffset.animateTo(0f, tween(50))
            }
            AnswerState.DEFAULT -> {
                scaleAnim.snapTo(1f)
                shakeOffset.snapTo(0f)
            }
        }
    }

    val containerColor = when (state) {
        AnswerState.DEFAULT -> MaterialTheme.colorScheme.primaryContainer
        AnswerState.CORRECT -> Color(0xFF2E7D32)
        AnswerState.WRONG -> Color(0xFFD32F2F)
    }
    val contentColor = when (state) {
        AnswerState.DEFAULT -> MaterialTheme.colorScheme.onSurface
        else -> Color.White
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .scale(scaleAnim.value)
            .offset { IntOffset(shakeOffset.value.dp.roundToPx(), 0) },
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
