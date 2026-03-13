package com.game254studios.kakaandchui.ui.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.BannerAdView
import com.game254studios.kakaandchui.ui.components.FlyingBee
import com.game254studios.kakaandchui.ui.components.ModuleCard
import com.game254studios.kakaandchui.ui.components.ParentalGate
import com.game254studios.kakaandchui.util.ImageLoader
import com.game254studios.kakaandchui.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    val bgBitmap by produceState<ImageBitmap?>(null) {
        value = withContext(Dispatchers.IO) { ImageLoader.load(context, "gfx/bg.png") }
    }
    val ubaoBitmap by produceState<ImageBitmap?>(null) {
        value = withContext(Dispatchers.IO) { ImageLoader.load(context, "gfx/ubao_00001.png") }
    }

    val cardSize = when {
        screenWidthDp >= 840 -> 150.dp
        screenWidthDp >= 600 -> 140.dp
        else -> 130.dp
    }

    val xpInLevel = homeState.xp % 100
    val xpProgress = xpInLevel / 100f

    var showParentalGate by remember { mutableStateOf(false) }

    val modules = Module.entries.toList()
    val bannerHeight = if (isPremium) 0.dp else 56.dp

    Box(modifier = Modifier.fillMaxSize()) {

        // Vibrant background
        bgBitmap?.let { bg ->
            Image(
                bitmap = bg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.6f
            )
        }
        // Warm gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x55FFF8E1),
                            Color(0x99FFF8E1),
                            Color(0xCCFFF8E1)
                        )
                    )
                )
        )

        // Animated Bee flying around
        FlyingBee()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = bannerHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Playful header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Mascot in a bubble
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    ubaoBitmap?.let { ubao ->
                        Image(
                            bitmap = ubao,
                            contentDescription = "Ubao mascot",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Kaka & Chui 🦜🐆",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Karibu tujifunze! 🌟",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Playful stats bubbles
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.85f),
                tonalElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Level badge
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🏆",
                            fontSize = 22.sp
                        )
                        Text(
                            text = "Lv.${homeState.level}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // XP with progress
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "⭐",
                            fontSize = 22.sp
                        )
                        Text(
                            text = "${homeState.xp} XP",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF9800)
                        )
                    }

                    // Coins
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🪙",
                            fontSize = 22.sp
                        )
                        Text(
                            text = "${homeState.coins}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB300)
                        )
                    }

                    // Streak
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🔥",
                            fontSize = 22.sp
                        )
                        Text(
                            text = "${homeState.streak}d",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5722)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Section title
            Text(
                text = "Chagua Somo 📚",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

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
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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

        // Settings gear in a bubble
        IconButton(
            onClick = { showParentalGate = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(8.dp)
                .sizeIn(minWidth = 44.dp, minHeight = 44.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.7f))
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

        // Banner ad
        BannerAdView(
            isPremium = isPremium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .fillMaxWidth()
        )
    }
}
