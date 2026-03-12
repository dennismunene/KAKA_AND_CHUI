package com.game254studios.kakaandchui.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.ModuleCard
import com.game254studios.kakaandchui.ui.components.ParentalGate
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val homeState by homeViewModel.state.collectAsState()
    val bgBitmap = remember {
        try {
            val stream = context.assets.open("gfx/mainmenu/menubg.png")
            BitmapFactory.decodeStream(stream)?.asImageBitmap()
        } catch (_: Exception) { null }
    }

    // Responsive card size
    val cardSize = when {
        screenWidthDp >= 840 -> 180.dp
        screenWidthDp >= 600 -> 200.dp
        else -> 140.dp
    }

    val xpInLevel = homeState.xp % 100
    val xpProgress = xpInLevel / 100f

    var showParentalGate by remember { mutableStateOf(false) }

    val modules = Module.entries.toList()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        if (bgBitmap != null) {
            Image(
                bitmap = bgBitmap,
                contentDescription = "Main menu background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kaka & Chui",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Gamification stats bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.85f)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Level + XP bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Lv.${homeState.level} ${homeState.levelName}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        LinearProgressIndicator(
                            progress = { xpProgress },
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${homeState.xp} XP",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Coins + Streak
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "🪙 ${homeState.coins}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB300)
                        )
                        Text(
                            text = "🔥 ${homeState.streak} days",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF5722)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Module grid - adaptive columns based on screen width
            val columns = when {
                screenWidthDp >= 840 -> 3
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

        // Settings gear icon
        IconButton(
            onClick = { showParentalGate = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .sizeIn(minWidth = 64.dp, minHeight = 64.dp)
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

        // Banner ad at bottom for free tier users
        BannerAdView(
            isPremium = isPremium,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
