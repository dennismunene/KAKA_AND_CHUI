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
                val afd = context.assets.openFd(assetPath)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.w("AudioPlayer", "Could not play asset: $assetPath", e)
        }
    }

    fun stop() {
        try {
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
    }
}
