package com.game254studios.kakaandchui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.game254studios.kakaandchui.analytics.AnalyticsManager
import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val profileName: String = "",
    val xp: Int = 0,
    val level: Int = 1,
    val levelName: String = "Chick",
    val coins: Int = 0,
    val streak: Int = 0,
    val moduleStars: Map<String, Int> = emptyMap()
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val db = KakaDatabase.getInstance(application)
    private val prefs = UserPreferences(application)
    val gameRepo = GameRepository(db, prefs)
    private val analyticsManager = AnalyticsManager.getInstance(application)

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadState()
    }

    fun logModuleStarted(moduleName: String) {
        analyticsManager.logModuleStarted(moduleName)
    }

    fun loadState() {
        viewModelScope.launch {
            val profile = gameRepo.getOrCreateDefaultProfile()
            val coins = gameRepo.getCoins(profile.id)
            val streak = gameRepo.getStreak(profile.id)
            val moduleStars = gameRepo.getModuleStars(profile.id)
            val level = gameRepo.getLevel(profile.xp)
            val levelName = gameRepo.getLevelName(level)

            _state.value = HomeState(
                profileName = profile.name,
                xp = profile.xp,
                level = level,
                levelName = levelName,
                coins = coins,
                streak = streak.currentStreak,
                moduleStars = moduleStars
            )
        }
    }
}
