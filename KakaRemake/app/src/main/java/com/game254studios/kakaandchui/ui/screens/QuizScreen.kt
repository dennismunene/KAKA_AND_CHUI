package com.game254studios.kakaandchui.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game254studios.kakaandchui.audio.AudioPlayer
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.AnswerState
import com.game254studios.kakaandchui.ui.components.ConfettiOverlay
import com.game254studios.kakaandchui.ui.components.QuizAnswerButton
import com.game254studios.kakaandchui.viewmodel.QuizViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(
    module: Module,
    onBack: () -> Unit,
    onQuizFinished: (score: Int, total: Int, xpEarned: Int, coinsEarned: Int) -> Unit,
    quizViewModel: QuizViewModel = viewModel()
) {
    val state by quizViewModel.state.collectAsState()
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }

    LaunchedEffect(module) {
        quizViewModel.loadModule(module)
    }

    // Play quiz audio prompt when question changes
    LaunchedEffect(state.currentIndex, state.currentItem) {
        state.currentItem?.let { item ->
            if (!state.answered) {
                delay(400)
                audioPlayer.playAsset(item.quizAudioAsset)
            }
        }
    }

    // Play feedback sound on answer
    LaunchedEffect(state.answered, state.selectedAnswer) {
        if (state.answered && state.selectedAnswer != null) {
            if (state.selectedAnswer == state.correctAnswer) {
                audioPlayer.playAsset("mfx/correct_answer.mp3")
            } else {
                audioPlayer.playAsset("mfx/sadtrumpet_funny.mp3")
            }
        }
    }

    // Navigate to result when finished
    LaunchedEffect(state.isFinished) {
        if (state.isFinished) {
            audioPlayer.stop()
            onQuizFinished(state.score, state.allItems.size, state.xpEarned, state.coinsEarned)
        }
    }

    val isTablet = LocalConfiguration.current.screenWidthDp >= 600

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    audioPlayer.stop()
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "${module.swahiliName} - Zoezi",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${state.currentIndex + 1}/${state.allItems.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Score
            Text(
                text = "Alama: ${state.score}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Question prompt - show current item image
            state.currentItem?.let { currentItem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val bitmap = remember(currentItem.imageAsset) {
                        try {
                            val stream = context.assets.open(currentItem.imageAsset)
                            BitmapFactory.decodeStream(stream)?.asImageBitmap()
                        } catch (_: Exception) { null }
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = currentItem.name,
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            text = "?",
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Feedback overlay
                    if (state.answered) {
                        val feedbackBitmap = remember(state.selectedAnswer) {
                            val path = if (state.selectedAnswer == state.correctAnswer)
                                "gfx/green_tick.png" else "gfx/red_x.png"
                            try {
                                val stream = context.assets.open(path)
                                BitmapFactory.decodeStream(stream)?.asImageBitmap()
                            } catch (_: Exception) { null }
                        }
                        if (feedbackBitmap != null) {
                            Image(
                                bitmap = feedbackBitmap,
                                contentDescription = if (state.selectedAnswer == state.correctAnswer) "Correct" else "Wrong",
                                modifier = Modifier.size(120.dp)
                            )
                        } else {
                            Text(
                                text = if (state.selectedAnswer == state.correctAnswer) "✓" else "✗",
                                style = MaterialTheme.typography.displayLarge,
                                color = if (state.selectedAnswer == state.correctAnswer)
                                    Color(0xFF2E7D32) else Color(0xFFD32F2F)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Answer buttons - responsive layout
            if (isTablet) {
                // Tablet: 2×2 grid of larger answer buttons
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    state.options.chunked(2).forEach { rowOptions ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowOptions.forEach { option ->
                                val answerState = when {
                                    !state.answered -> AnswerState.DEFAULT
                                    option.id == state.correctAnswer -> AnswerState.CORRECT
                                    option.id == state.selectedAnswer -> AnswerState.WRONG
                                    else -> AnswerState.DEFAULT
                                }
                                QuizAnswerButton(
                                    text = option.name,
                                    state = answerState,
                                    enabled = !state.answered,
                                    onClick = { quizViewModel.selectAnswer(option.id) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowOptions.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            } else {
                // Phone: column layout
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    state.options.forEach { option ->
                        val answerState = when {
                            !state.answered -> AnswerState.DEFAULT
                            option.id == state.correctAnswer -> AnswerState.CORRECT
                            option.id == state.selectedAnswer -> AnswerState.WRONG
                            else -> AnswerState.DEFAULT
                        }
                        QuizAnswerButton(
                            text = option.name,
                            state = answerState,
                            enabled = !state.answered,
                            onClick = { quizViewModel.selectAnswer(option.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Next button after answering
            if (state.answered) {
                Button(
                    onClick = { quizViewModel.nextQuestion() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .height(48.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "Endelea ➡",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Confetti celebration on correct answer
        ConfettiOverlay(
            isVisible = state.answered && state.selectedAnswer == state.correctAnswer
        )
    }
}
