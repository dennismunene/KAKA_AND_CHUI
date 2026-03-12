package com.game254studios.kakaandchui.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .padding(horizontal = 24.dp, vertical = 4.dp),
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
