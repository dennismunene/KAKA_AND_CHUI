package com.game254studios.kakaandchui.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

/**
 * Singleton background music player that loops bgmusic.mp3.
 */
object BackgroundMusicPlayer {

    private const val TAG = "BackgroundMusicPlayer"
    private const val ASSET_PATH = "mfx/bgmusic.mp3"
    private const val DEFAULT_VOLUME = 0.3f

    private var mediaPlayer: MediaPlayer? = null
    private var currentVolume: Float = DEFAULT_VOLUME

    fun start(context: Context) {
        if (mediaPlayer != null) return
        try {
            val afd = context.assets.openFd(ASSET_PATH)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                isLooping = true
                setVolume(currentVolume, currentVolume)
                setOnErrorListener { _, what, extra ->
                    Log.w(TAG, "MediaPlayer error: what=$what extra=$extra")
                    releasePlayer()
                    true
                }
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not start background music", e)
            releasePlayer()
        }
    }

    fun pause() {
        try {
            mediaPlayer?.takeIf { it.isPlaying }?.pause()
        } catch (e: Exception) {
            Log.w(TAG, "Error pausing music", e)
        }
    }

    fun resume() {
        try {
            mediaPlayer?.takeIf { !it.isPlaying }?.start()
        } catch (e: Exception) {
            Log.w(TAG, "Error resuming music", e)
        }
    }

    fun stop() {
        releasePlayer()
    }

    fun setVolume(volume: Float) {
        currentVolume = volume.coerceIn(0f, 1f)
        try {
            mediaPlayer?.setVolume(currentVolume, currentVolume)
        } catch (e: Exception) {
            Log.w(TAG, "Error setting volume", e)
        }
    }

    private fun releasePlayer() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
        } catch (_: Exception) {
            try { mediaPlayer?.release() } catch (_: Exception) {}
        }
        mediaPlayer = null
    }
}
