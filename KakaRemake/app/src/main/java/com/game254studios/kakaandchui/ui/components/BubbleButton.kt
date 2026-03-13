package com.game254studios.kakaandchui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import com.game254studios.kakaandchui.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class BubbleColor(val assetPath: String) {
    RED("gfx/bubblebuttons/extracted/rect_0.png"),
    GREEN("gfx/bubblebuttons/extracted/rect_5.png"),
    YELLOW("gfx/bubblebuttons/extracted/rect_6.png"),
    WHITE("gfx/bubblebuttons/extracted/rect_1.png"),
    GRAY("gfx/bubblebuttons/extracted/rect_3.png")
}

/**
 * A kid-friendly button styled with bubble button artwork from the sprite sheet.
 * Uses extracted rect bubble images as backgrounds.
 */
@Composable
fun BubbleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: BubbleColor = BubbleColor.GREEN,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    textColor: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
    content: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgBitmap by produceState<ImageBitmap?>(null, color.assetPath) {
        value = withContext(Dispatchers.IO) { ImageLoader.load(context, color.assetPath) }
    }

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 56.dp)
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
                alpha = if (enabled) 1f else 0.5f
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        bgBitmap?.let { bg ->
            Image(
                bitmap = bg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        if (content != null) {
            Box(modifier = Modifier.padding(contentPadding)) {
                content()
            }
        } else {
            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}
