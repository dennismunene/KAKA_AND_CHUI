package com.game254studios.kakaandchui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.game254studios.kakaandchui.ui.theme.StarGold

@Composable
fun StarRating(
    stars: Int,
    maxStars: Int = 3,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.semantics { contentDescription = "$stars out of $maxStars stars" },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 1..maxStars) {
            Icon(
                imageVector = if (i <= stars) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= stars) StarGold else Color.Gray.copy(alpha = 0.4f),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
