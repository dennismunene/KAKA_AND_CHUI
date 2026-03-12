package com.game254studios.kakaandchui.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class RemoteConfigManager {

    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(12 * 60 * 60) // 12 hours
            .build()
        remoteConfig.setConfigSettingsAsync(settings)
        remoteConfig.setDefaultsAsync(defaults)
    }

    fun fetchAndActivate(onComplete: ((Boolean) -> Unit)? = null) {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            onComplete?.invoke(task.isSuccessful)
        }
    }

    fun getInt(key: String): Int = remoteConfig.getLong(key).toInt()

    fun getDouble(key: String): Double = remoteConfig.getDouble(key)

    fun getString(key: String): String = remoteConfig.getString(key)

    fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)

    companion object {
        const val KEY_DAILY_COIN_BONUS = "daily_coin_bonus"
        const val KEY_STREAK_MULTIPLIER = "streak_multiplier"
        const val KEY_AD_COOLDOWN_SECONDS = "ad_cooldown_seconds"
        const val KEY_MAX_DAILY_ADS = "max_daily_ads"
        const val KEY_FEATURED_MODULE = "featured_module"

        private val defaults: Map<String, Any> = mapOf(
            KEY_DAILY_COIN_BONUS to 10L,
            KEY_STREAK_MULTIPLIER to 1.5,
            KEY_AD_COOLDOWN_SECONDS to 300L,
            KEY_MAX_DAILY_ADS to 10L,
            KEY_FEATURED_MODULE to ""
        )

        @Volatile
        private var instance: RemoteConfigManager? = null

        fun getInstance(): RemoteConfigManager {
            return instance ?: synchronized(this) {
                instance ?: RemoteConfigManager().also { instance = it }
            }
        }
    }
}
