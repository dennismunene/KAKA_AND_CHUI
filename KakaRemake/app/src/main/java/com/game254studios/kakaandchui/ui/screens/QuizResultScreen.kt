package com.game254studios.kakaandchui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.game254studios.kakaandchui.audio.AudioPlayer
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.StarRating

@Composable
fun QuizResultScreen(
    module: Module,
    score: Int,
    total: Int,
    onPlayAgain: () -> Unit,
    onBackToHome: () -> Unit
) {
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }

    val percentage = if (total > 0) score.toFloat() / total else 0f
    val stars = when {
        percentage >= 0.9f -> 3
        percentage >= 0.6f -> 2
        percentage > 0f -> 1
        else -> 0
    }

    // Play celebration audio
    LaunchedEffect(Unit) {
        if (percentage >= 0.6f) {
            audioPlayer.playAsset("mfx/clapping.mp3")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${module.swahiliName} - Matokeo",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Emoji feedback
        Text(
            text = when {
                percentage >= 0.9f -> "🎉"
                percentage >= 0.6f -> "😊"
                else -> "💪"
            },
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        StarRating(stars = stars)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = when {
                percentage >= 0.9f -> "Bora sana! 🌟"
                percentage >= 0.6f -> "Vizuri! 👏"
                else -> "Jaribu tena! 🔄"
            },
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                audioPlayer.stop()
                onPlayAgain()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Cheza Tena 🔄",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                audioPlayer.stop()
                onBackToHome()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Nyumbani 🏠",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
