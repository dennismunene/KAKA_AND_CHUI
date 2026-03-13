package com.game254studios.kakaandchui.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game254studios.kakaandchui.audio.AudioPlayer
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.BubbleButton
import com.game254studios.kakaandchui.ui.components.BubbleColor
import com.game254studios.kakaandchui.ui.components.ConfettiOverlay
import com.game254studios.kakaandchui.viewmodel.SoundMatchViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun SoundMatchScreen(
    module: Module,
    onBack: () -> Unit,
    onGameFinished: (score: Int, total: Int, xpEarned: Int, coinsEarned: Int) -> Unit,
    soundMatchViewModel: SoundMatchViewModel = viewModel()
) {
    val state by soundMatchViewModel.state.collectAsState()
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(module) {
        soundMatchViewModel.loadModule(module)
    }

    // Play audio prompt when round changes
    LaunchedEffect(state.currentIndex, state.currentItem) {
        state.currentItem?.let { item ->
            if (!state.answered) {
                delay(500)
                audioPlayer.playAsset(item.audioAsset)
            }
        }
    }

    // Feedback sounds
    LaunchedEffect(state.answered, state.selectedAnswer) {
        if (state.answered && state.selectedAnswer != null) {
            if (state.isCorrect) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                audioPlayer.playAsset("mfx/correct_answer.mp3")
            } else {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                audioPlayer.playAsset("mfx/sadtrumpet_funny.mp3")
            }
        }
    }

    // Navigate to result when finished
    LaunchedEffect(state.isFinished) {
        if (state.isFinished) {
            delay(1500)
            audioPlayer.stop()
            onGameFinished(state.score, state.totalItems, state.xpEarned, state.coinsEarned)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .systemBarsPadding()
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        audioPlayer.stop()
                        onBack()
                    },
                    modifier = Modifier.sizeIn(minWidth = 64.dp, minHeight = 64.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "${module.swahiliName} - Sikia 🔊",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${state.currentIndex + 1}/${state.totalItems}",
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

            // Speaker button to replay audio
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        state.currentItem?.let { item ->
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            audioPlayer.playAsset(item.audioAsset)
                        }
                    },
                    modifier = Modifier
                        .sizeIn(minWidth = 80.dp, minHeight = 80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play sound again",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "Sikiliza, kisha gusa picha sahihi!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2×2 image grid
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                state.options.chunked(2).forEach { rowOptions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowOptions.forEach { option ->
                            SoundOptionCard(
                                imageAsset = option.imageAsset,
                                name = option.name,
                                isSelected = state.answered && state.selectedAnswer == option.id,
                                isCorrectAnswer = state.answered && option.id == state.currentItem?.id,
                                isWrongSelection = state.answered && state.selectedAnswer == option.id && !state.isCorrect,
                                showResult = state.answered,
                                enabled = !state.answered,
                                onClick = {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    soundMatchViewModel.selectAnswer(option.id)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowOptions.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Next button after answering
            if (state.answered && !state.isFinished) {
                BubbleButton(
                    text = "Endelea ➡",
                    onClick = { soundMatchViewModel.nextRound() },
                    color = BubbleColor.GREEN,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .height(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        ConfettiOverlay(
            isVisible = state.answered && state.isCorrect
        )
    }
}

@Composable
private fun SoundOptionCard(
    imageAsset: String,
    name: String,
    isSelected: Boolean,
    isCorrectAnswer: Boolean,
    isWrongSelection: Boolean,
    showResult: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Shake animation for wrong answer
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(isWrongSelection) {
        if (isWrongSelection) {
            repeat(3) {
                shakeOffset.animateTo(8f, tween(50))
                shakeOffset.animateTo(-8f, tween(50))
            }
            shakeOffset.animateTo(0f, tween(50))
        }
    }

    // Scale animation for correct answer
    val scale = remember { Animatable(1f) }
    LaunchedEffect(isCorrectAnswer, showResult) {
        if (isCorrectAnswer && showResult) {
            scale.animateTo(1.05f, spring(stiffness = Spring.StiffnessLow))
            scale.animateTo(1f, spring(stiffness = Spring.StiffnessLow))
        }
    }

    val borderColor = when {
        showResult && isCorrectAnswer -> Color(0xFF4CAF50)
        showResult && isWrongSelection -> Color(0xFFF44336)
        else -> Color.Transparent
    }

    val borderWidth = if (showResult && (isCorrectAnswer || isWrongSelection)) 4.dp else 0.dp

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .sizeIn(minWidth = 64.dp, minHeight = 64.dp)
            .offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .then(
                if (borderWidth > 0.dp) Modifier.border(borderWidth, borderColor, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(enabled = enabled) { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                showResult && isCorrectAnswer -> Color(0xFFE8F5E9)
                showResult && isWrongSelection -> Color(0xFFFFEBEE)
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            val bitmap = remember(imageAsset) {
                try {
                    val stream = context.assets.open(imageAsset)
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()
                } catch (_: Exception) { null }
            }
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
