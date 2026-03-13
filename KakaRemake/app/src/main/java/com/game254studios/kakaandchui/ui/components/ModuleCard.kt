package com.game254studios.kakaandchui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.theme.StarGold
import com.game254studios.kakaandchui.util.ImageLoader

@Composable
fun ModuleCard(
    module: Module,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    stars: Int = 0,
    cardSize: Dp = 140.dp
) {
    val context = LocalContext.current
    val bitmap = remember(module.iconAsset) {
        ImageLoader.load(context, module.iconAsset)
    }
    val moduleColor = Color(module.colorHex)
    val lighterColor = moduleColor.copy(alpha = 0.3f)

    Surface(
        modifier = modifier
            .size(cardSize)
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(lighterColor, Color.White)
                    )
                )
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular icon bubble
            Box(
                modifier = Modifier
                    .size(cardSize * 0.48f)
                    .clip(CircleShape)
                    .background(moduleColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "${module.swahiliName} - ${module.displayName}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text(
                        text = module.emoji,
                        fontSize = (cardSize.value * 0.25f).sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Module name
            Text(
                text = "${module.emoji} ${module.swahiliName}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = moduleColor,
                maxLines = 1
            )

            // Stars
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "$stars out of 3 stars" },
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..3) {
                    Icon(
                        imageVector = if (i <= stars) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (i <= stars) StarGold else Color.Gray.copy(alpha = 0.3f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
