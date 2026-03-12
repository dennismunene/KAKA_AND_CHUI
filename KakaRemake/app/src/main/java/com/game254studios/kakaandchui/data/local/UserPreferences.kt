package com.game254studios.kakaandchui.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
        val SFX_ENABLED = booleanPreferencesKey("sfx_enabled")
        val ACTIVE_PROFILE_ID = intPreferencesKey("active_profile_id")
        val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
        val IS_PREMIUM = booleanPreferencesKey("is_premium")
        val DAILY_TIME_LIMIT_MINUTES = intPreferencesKey("daily_time_limit_minutes")
    }

    val musicEnabled: Flow<Boolean> = context.dataStore.data.map { it[MUSIC_ENABLED] ?: true }
    val sfxEnabled: Flow<Boolean> = context.dataStore.data.map { it[SFX_ENABLED] ?: true }
    val activeProfileId: Flow<Int> = context.dataStore.data.map { it[ACTIVE_PROFILE_ID] ?: -1 }
    val hasCompletedOnboarding: Flow<Boolean> = context.dataStore.data.map { it[HAS_COMPLETED_ONBOARDING] ?: false }
    val isPremium: Flow<Boolean> = context.dataStore.data.map { it[IS_PREMIUM] ?: false }
    val dailyTimeLimitMinutes: Flow<Int> = context.dataStore.data.map { it[DAILY_TIME_LIMIT_MINUTES] ?: 0 }

    suspend fun setMusicEnabled(enabled: Boolean) {
        context.dataStore.edit { it[MUSIC_ENABLED] = enabled }
    }

    suspend fun setSfxEnabled(enabled: Boolean) {
        context.dataStore.edit { it[SFX_ENABLED] = enabled }
    }

    suspend fun setActiveProfileId(id: Int) {
        context.dataStore.edit { it[ACTIVE_PROFILE_ID] = id }
    }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { it[HAS_COMPLETED_ONBOARDING] = true }
    }

    suspend fun setPremium(premium: Boolean) {
        context.dataStore.edit { it[IS_PREMIUM] = premium }
    }

    suspend fun setDailyTimeLimit(minutes: Int) {
        context.dataStore.edit { it[DAILY_TIME_LIMIT_MINUTES] = minutes }
    }
}
