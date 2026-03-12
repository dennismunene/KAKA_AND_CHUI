package com.game254studios.kakaandchui.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AudioPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playAsset(assetPath: String) {
        stop()
        try {
            mediaPlayer = MediaPlayer().apply {
                setOnErrorListener { _, what, extra ->
                    Log.w("AudioPlayer", "MediaPlayer error: what=$what extra=$extra")
                    release()
                    true
                }
                val afd = context.assets.openFd(assetPath)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                start()
            }
        } catch (e: OutOfMemoryError) {
            Log.e("AudioPlayer", "OOM loading audio: $assetPath", e)
            releasePlayer()
        } catch (e: Exception) {
            Log.w("AudioPlayer", "Could not play asset: $assetPath", e)
            releasePlayer()
        }
    }

    fun stop() {
        releasePlayer()
    }

    /** Fully release all MediaPlayer resources. */
    fun release() {
        releasePlayer()
    }

    private fun releasePlayer() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                }
                release()
            }
        } catch (_: Exception) {
            try { mediaPlayer?.release() } catch (_: Exception) {}
        }
        mediaPlayer = null
    }
}
