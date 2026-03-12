package com.game254studios.kakaandchui.ui.screens

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.ModuleCard
import com.game254studios.kakaandchui.ui.components.ParentalGate
import com.game254studios.kakaandchui.ui.components.SpriteAnimation
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.game254studios.kakaandchui.util.ImageLoader
import com.game254studios.kakaandchui.viewmodel.HomeViewModel
import com.game254studios.kakaandchui.ui.components.BannerAdView

@Composable
fun HomeScreen(
    onModuleClick: (Module) -> Unit,
    homeViewModel: HomeViewModel,
    onNavigateToParentZone: () -> Unit = {},
    isPremium: Boolean = false
) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp
    val screenHeightDp = config.screenHeightDp
    val isLandscape = screenWidthDp > screenHeightDp
    val homeState by homeViewModel.state.collectAsState()

    val characterBitmap = remember { ImageLoader.load(context, "gfx/happyanim.png") }

    // Title row with animated character
    // happyanim.png is a 3-column × 4-row sprite sheet (12 frames)
    val hasHappyAnim = remember {
        try { context.assets.open("gfx/happyanim.png").close(); true }
        catch (_: Exception) { false }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "idle_bounce")
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_bounce"
    )

    val cardSize = when {
        screenWidthDp >= 840 -> 160.dp
        screenWidthDp >= 600 -> 150.dp
        else -> 120.dp
    }

    val xpInLevel = homeState.xp % 100
    val xpProgress = xpInLevel / 100f

    var showParentalGate by remember { mutableStateOf(false) }

    val modules = Module.entries.toList()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title row with character
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (hasHappyAnim) {
                    SpriteAnimation(
                        assetPath = "gfx/happyanim.png",
                        columns = 3, rows = 4, frameCount = 12,
                        frameDurationMs = 120L,
                        size = 52.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = "Kaka & Chui",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Compact stats bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.85f)
                )
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Lv.${homeState.level} ${homeState.levelName}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        LinearProgressIndicator(
                            progress = { xpProgress },
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${homeState.xp} XP",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "🪙 ${homeState.coins}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB300)
                        )
                        Text(
                            text = "🔥 ${homeState.streak} days",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5722)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Module grid
            val columns = when {
                isLandscape && screenWidthDp >= 840 -> 4
                isLandscape -> 3
                screenWidthDp >= 600 -> 3
                else -> 2
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(modules) { module ->
                    ModuleCard(
                        module = module,
                        onClick = { onModuleClick(module) },
                        stars = homeState.moduleStars[module.name] ?: 0,
                        cardSize = cardSize
                    )
                }
            }
        }

        // Settings gear icon
        IconButton(
            onClick = { showParentalGate = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings - Parent Zone",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        if (showParentalGate) {
            ParentalGate(
                onPassed = {
                    showParentalGate = false
                    onNavigateToParentZone()
                },
                onDismissed = { showParentalGate = false }
            )
        }

        // Banner ad at bottom
        BannerAdView(
            isPremium = isPremium,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
