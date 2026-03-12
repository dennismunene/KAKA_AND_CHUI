package com.game254studios.kakaandchui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Singleton image loader with LRU memory cache for decoded bitmaps.
 * Avoids redundant decoding across screens and recompositions.
 */
object ImageLoader {

    private const val TAG = "ImageLoader"
    private const val MAX_CACHE_MB = 20
    private const val MAX_TEXTURE_SIZE = 2048

    private val cache: LruCache<String, ImageBitmap> by lazy {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = minOf(MAX_CACHE_MB * 1024, maxMemory / 8)
        object : LruCache<String, ImageBitmap>(cacheSize) {
            override fun sizeOf(key: String, value: ImageBitmap): Int {
                return (value.width * value.height * 4) / 1024
            }
        }
    }

    /**
     * Load a bitmap from assets with caching and automatic downscaling.
     * Returns null if the asset cannot be decoded.
     */
    fun load(context: Context, assetPath: String): ImageBitmap? {
        cache.get(assetPath)?.let { return it }

        return try {
            val options = BitmapFactory.Options()

            // First pass: decode bounds only
            options.inJustDecodeBounds = true
            context.assets.open(assetPath).use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            // Calculate inSampleSize for large images
            options.inSampleSize = calculateInSampleSize(
                options.outWidth, options.outHeight,
                MAX_TEXTURE_SIZE, MAX_TEXTURE_SIZE
            )
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            // Second pass: decode the bitmap
            val bitmap = context.assets.open(assetPath).use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            bitmap?.asImageBitmap()?.also { imageBitmap ->
                cache.put(assetPath, imageBitmap)
            }
        } catch (e: OutOfMemoryError) {
            Log.w(TAG, "OOM loading asset: $assetPath", e)
            null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to load asset: $assetPath", e)
            null
        }
    }

    /**
     * Preload all images for a module's asset directory.
     */
    fun preloadModule(context: Context, moduleDir: String) {
        try {
            val files = context.assets.list(moduleDir) ?: return
            for (file in files) {
                val path = "$moduleDir/$file"
                if (file.endsWith(".png", true) || file.endsWith(".jpg", true) ||
                    file.endsWith(".jpeg", true) || file.endsWith(".webp", true)
                ) {
                    load(context, path)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to preload module: $moduleDir", e)
        }
    }

    private fun calculateInSampleSize(
        rawWidth: Int, rawHeight: Int,
        maxWidth: Int, maxHeight: Int
    ): Int {
        var inSampleSize = 1
        if (rawWidth > maxWidth || rawHeight > maxHeight) {
            val halfWidth = rawWidth / 2
            val halfHeight = rawHeight / 2
            while ((halfWidth / inSampleSize) >= maxWidth &&
                (halfHeight / inSampleSize) >= maxHeight
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /** Clear the entire image cache. */
    fun clearCache() {
        cache.evictAll()
    }
}
