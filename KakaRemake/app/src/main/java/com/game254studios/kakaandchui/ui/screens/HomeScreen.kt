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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.game254studios.kakaandchui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onModuleClick: (Module) -> Unit,
    homeViewModel: HomeViewModel
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
        else -> 160.dp
    }

    val xpInLevel = homeState.xp % 100
    val xpProgress = xpInLevel / 100f

    Box(modifier = Modifier.fillMaxSize()) {
        // Background
        if (bgBitmap != null) {
            Image(
                bitmap = bgBitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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

            if (screenWidthDp >= 840) {
                // Expanded (10" tablets): 4×1 row layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Module.entries.forEach { module ->
                        ModuleCard(
                            module = module,
                            onClick = { onModuleClick(module) },
                            stars = homeState.moduleStars[module.name] ?: 0,
                            cardSize = cardSize
                        )
                    }
                }
            } else {
                // Compact & Medium: 2×2 grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ModuleCard(
                        module = Module.VOKALI,
                        onClick = { onModuleClick(Module.VOKALI) },
                        stars = homeState.moduleStars[Module.VOKALI.name] ?: 0,
                        cardSize = cardSize
                    )
                    ModuleCard(
                        module = Module.TARAKIMU,
                        onClick = { onModuleClick(Module.TARAKIMU) },
                        stars = homeState.moduleStars[Module.TARAKIMU.name] ?: 0,
                        cardSize = cardSize
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ModuleCard(
                        module = Module.MAUMBO,
                        onClick = { onModuleClick(Module.MAUMBO) },
                        stars = homeState.moduleStars[Module.MAUMBO.name] ?: 0,
                        cardSize = cardSize
                    )
                    ModuleCard(
                        module = Module.RANGI,
                        onClick = { onModuleClick(Module.RANGI) },
                        stars = homeState.moduleStars[Module.RANGI.name] ?: 0,
                        cardSize = cardSize
                    )
                }
            }
        }
    }
}
