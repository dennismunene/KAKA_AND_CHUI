package com.game254studios.kakaandchui.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.game254studios.kakaandchui.audio.AudioPlayer
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.ConfettiOverlay
import com.game254studios.kakaandchui.viewmodel.MemoryCard
import com.game254studios.kakaandchui.viewmodel.MemoryMatchViewModel
import kotlinx.coroutines.delay

@Composable
fun MemoryMatchScreen(
    module: Module,
    onBack: () -> Unit,
    memoryMatchViewModel: MemoryMatchViewModel = viewModel()
) {
    val state by memoryMatchViewModel.state.collectAsState()
    val context = LocalContext.current
    val audioPlayer = remember { AudioPlayer(context) }
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(module) {
        memoryMatchViewModel.loadModule(module)
    }

    // Play audio when a card is flipped
    LaunchedEffect(state.lastMatchedAudio) {
        state.lastMatchedAudio?.let { audio ->
            audioPlayer.playAsset(audio)
        }
    }

    // Handle match checking with delay so player can see both cards
    LaunchedEffect(state.secondFlipped) {
        if (state.secondFlipped != null) {
            delay(800)
            memoryMatchViewModel.checkMatch()
        }
    }

    // Play celebration sound on match
    LaunchedEffect(state.showCelebration, state.matchesFound) {
        if (state.showCelebration && !state.isFinished) {
            audioPlayer.playAsset("mfx/correct_answer.mp3")
            delay(1200)
            memoryMatchViewModel.dismissCelebration()
        }
    }

    // Play celebration on game finish
    LaunchedEffect(state.isFinished) {
        if (state.isFinished) {
            audioPlayer.playAsset("mfx/clapping.mp3")
        }
    }

    val columns = if (state.cards.size <= 8) 3 else 4

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
                    text = "${module.swahiliName} - Kumbuka 🃏",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }

            // Score display
            Text(
                text = "Jozi: ${state.matchesFound}/${state.totalPairs}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.isFinished) {
                // Victory screen
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🎉 Hongera! 🎉",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Umepata jozi zote!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "⭐ XP +${state.xpEarned}  🪙 +${state.coinsEarned}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            audioPlayer.stop()
                            onBack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Rudi Nyumbani 🏠",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { memoryMatchViewModel.loadModule(module) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = "Cheza Tena! 🔄",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }
                }
            } else {
                // Card grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.cards, key = { it.id }) { card ->
                        MemoryCardItem(
                            card = card,
                            onClick = {
                                if (!state.isChecking && !card.isFlipped && !card.isMatched) {
                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    memoryMatchViewModel.flipCard(card.id)
                                }
                            }
                        )
                    }
                }
            }
        }

        ConfettiOverlay(isVisible = state.showCelebration)
    }
}

@Composable
private fun MemoryCardItem(
    card: MemoryCard,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val rotation by animateFloatAsState(
        targetValue = if (card.isFlipped || card.isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "card_flip"
    )

    Card(
        modifier = Modifier
            .aspectRatio(0.8f)
            .sizeIn(minWidth = 64.dp, minHeight = 64.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(enabled = !card.isFlipped && !card.isMatched) { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                card.isMatched -> Color(0xFFE8F5E9)
                card.isFlipped -> Color.White
                else -> MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation > 90f) {
                // Front side - show image (rendered mirrored to compensate rotation)
                val bitmap = remember(card.item.imageAsset) {
                    try {
                        val stream = context.assets.open(card.item.imageAsset)
                        BitmapFactory.decodeStream(stream)?.asImageBitmap()
                    } catch (_: Exception) { null }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = card.item.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Text(
                            text = card.item.name,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // Back side - "?"
                Text(
                    text = "❓",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
