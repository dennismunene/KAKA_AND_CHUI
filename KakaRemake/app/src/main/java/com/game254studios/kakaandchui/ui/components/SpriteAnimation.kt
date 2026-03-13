package com.game254studios.kakaandchui.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

/**
 * Animates a sprite strip/sheet loaded from assets.
 *
 * @param assetPath Path to the sprite sheet PNG in assets
 * @param columns Number of columns in the sheet
 * @param rows Number of rows in the sheet
 * @param frameCount Total frames to animate (may be less than columns*rows)
 * @param frameDurationMs Milliseconds per frame
 * @param size Display size (square) of the animation
 */
@Composable
fun SpriteAnimation(
    assetPath: String,
    columns: Int,
    rows: Int,
    frameCount: Int = columns * rows,
    frameDurationMs: Long = 120L,
    size: Dp,
    modifier: Modifier = Modifier
) {
    SpriteAnimation(
        assetPath = assetPath,
        columns = columns,
        rows = rows,
        frameCount = frameCount,
        frameDurationMs = frameDurationMs,
        spriteWidth = size,
        spriteHeight = size,
        modifier = modifier
    )
}

/**
 * Animates a sprite strip/sheet with independent width and height.
 */
@Composable
fun SpriteAnimation(
    assetPath: String,
    columns: Int,
    rows: Int,
    frameCount: Int = columns * rows,
    frameDurationMs: Long = 120L,
    spriteWidth: Dp,
    spriteHeight: Dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sheetBitmap = remember(assetPath) {
        try {
            val stream = context.assets.open(assetPath)
            BitmapFactory.decodeStream(stream)
        } catch (_: Exception) { null }
    }

    var currentFrame by remember { mutableIntStateOf(0) }

    LaunchedEffect(assetPath) {
        while (true) {
            delay(frameDurationMs)
            currentFrame = (currentFrame + 1) % frameCount
        }
    }

    if (sheetBitmap != null) {
        val frameWidth = sheetBitmap.width / columns
        val frameHeight = sheetBitmap.height / rows

        val col = currentFrame % columns
        val row = currentFrame / columns

        val frameBitmap = remember(currentFrame, assetPath) {
            Bitmap.createBitmap(
                sheetBitmap,
                col * frameWidth,
                row * frameHeight,
                frameWidth,
                frameHeight
            ).asImageBitmap()
        }

        Canvas(modifier = modifier.width(spriteWidth).height(spriteHeight)) {
            drawImage(
                image = frameBitmap,
                srcOffset = IntOffset.Zero,
                srcSize = IntSize(frameBitmap.width, frameBitmap.height),
                dstSize = IntSize(this.size.width.toInt(), this.size.height.toInt())
            )
        }
    }
}
