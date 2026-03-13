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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import androidx.compose.runtime.produceState

private const val MAX_SHEET_PIXELS = 2048 * 2048

/**
 * Animates a sprite strip/sheet loaded from assets (square output).
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
 * Frames are pre-extracted once on a background thread to avoid
 * main-thread bitmap allocations and ANRs.
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

    // Load sprite sheet and pre-extract all frames off the main thread
    val frames by produceState<List<ImageBitmap>?>(initialValue = null, key1 = assetPath) {
        value = withContext(Dispatchers.IO) {
            try {
                val stream = context.assets.open(assetPath)

                // First pass: check dimensions and compute sample size
                val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                val sizeStream = context.assets.open(assetPath)
                BitmapFactory.decodeStream(sizeStream, null, opts)
                sizeStream.close()

                val totalPixels = opts.outWidth.toLong() * opts.outHeight.toLong()
                var sampleSize = 1
                while (totalPixels / (sampleSize * sampleSize) > MAX_SHEET_PIXELS) {
                    sampleSize *= 2
                }

                val decodeOpts = BitmapFactory.Options().apply { inSampleSize = sampleSize }
                val sheet = BitmapFactory.decodeStream(stream, null, decodeOpts) ?: return@withContext null
                stream.close()

                val fw = sheet.width / columns
                val fh = sheet.height / rows

                val extractedFrames = (0 until frameCount).map { i ->
                    val col = i % columns
                    val row = i / columns
                    Bitmap.createBitmap(sheet, col * fw, row * fh, fw, fh).asImageBitmap()
                }

                // Release the full sheet since frames are extracted
                if (!sheet.isRecycled) sheet.recycle()

                extractedFrames
            } catch (_: Exception) {
                null
            }
        }
    }

    var currentFrame by remember { mutableIntStateOf(0) }

    LaunchedEffect(assetPath, frames) {
        if (frames == null) return@LaunchedEffect
        while (true) {
            delay(frameDurationMs)
            currentFrame = (currentFrame + 1) % frameCount
        }
    }

    val frameList = frames ?: return
    if (currentFrame >= frameList.size) return

    val frame = frameList[currentFrame]

    Canvas(modifier = modifier.width(spriteWidth).height(spriteHeight)) {
        drawImage(
            image = frame,
            srcOffset = IntOffset.Zero,
            srcSize = IntSize(frame.width, frame.height),
            dstSize = IntSize(this.size.width.toInt(), this.size.height.toInt())
        )
    }
}
