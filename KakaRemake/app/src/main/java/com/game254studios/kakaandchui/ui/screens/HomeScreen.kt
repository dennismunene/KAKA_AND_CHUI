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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.ModuleCard

@Composable
fun HomeScreen(onModuleClick: (Module) -> Unit) {
    val context = LocalContext.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val bgBitmap = remember {
        try {
            val stream = context.assets.open("gfx/mainmenu/menubg.png")
            BitmapFactory.decodeStream(stream)?.asImageBitmap()
        } catch (_: Exception) { null }
    }

    // Responsive card size
    val cardSize = when {
        screenWidthDp >= 840 -> 180.dp  // Expanded (10" tablets)
        screenWidthDp >= 600 -> 200.dp  // Medium (7" tablets)
        else -> 160.dp                   // Compact (phones)
    }

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
            Spacer(modifier = Modifier.height(32.dp))

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
                            stars = 0,
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
                        stars = 0,
                        cardSize = cardSize
                    )
                    ModuleCard(
                        module = Module.TARAKIMU,
                        onClick = { onModuleClick(Module.TARAKIMU) },
                        stars = 0,
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
                        stars = 0,
                        cardSize = cardSize
                    )
                    ModuleCard(
                        module = Module.RANGI,
                        onClick = { onModuleClick(Module.RANGI) },
                        stars = 0,
                        cardSize = cardSize
                    )
                }
            }
        }
    }
}
