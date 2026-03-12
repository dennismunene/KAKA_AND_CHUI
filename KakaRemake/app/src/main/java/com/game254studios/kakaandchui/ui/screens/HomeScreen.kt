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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.components.ModuleCard

@Composable
fun HomeScreen(onModuleClick: (Module) -> Unit) {
    val context = LocalContext.current
    val bgBitmap = remember {
        try {
            val stream = context.assets.open("gfx/mainmenu/menubg.png")
            BitmapFactory.decodeStream(stream)?.asImageBitmap()
        } catch (_: Exception) { null }
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

            // 2x2 grid of module cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModuleCard(
                    module = Module.VOKALI,
                    onClick = { onModuleClick(Module.VOKALI) }
                )
                ModuleCard(
                    module = Module.TARAKIMU,
                    onClick = { onModuleClick(Module.TARAKIMU) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModuleCard(
                    module = Module.MAUMBO,
                    onClick = { onModuleClick(Module.MAUMBO) }
                )
                ModuleCard(
                    module = Module.RANGI,
                    onClick = { onModuleClick(Module.RANGI) }
                )
            }
        }
    }
}
