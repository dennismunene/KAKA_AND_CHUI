package com.game254studios.kakaandchui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.model.Module
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParentViewModel(application: Application) : AndroidViewModel(application) {
    private val db = KakaDatabase.getInstance(application)
    private val prefs = UserPreferences(application)

    data class ParentState(
        val musicEnabled: Boolean = true,
        val sfxEnabled: Boolean = true,
        val moduleProgress: List<ModuleProgressSummary> = emptyList(),
        val totalXp: Int = 0,
        val level: String = "Yai (Egg)",
        val streak: Int = 0,
        val coins: Int = 0
    )

    data class ModuleProgressSummary(
        val moduleName: String,
        val bestScore: String,
        val stars: Int
    )

    private val _state = MutableStateFlow(ParentState())
    val state: StateFlow<ParentState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.musicEnabled.collect { enabled ->
                _state.update { it.copy(musicEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            prefs.sfxEnabled.collect { enabled ->
                _state.update { it.copy(sfxEnabled = enabled) }
            }
        }
        viewModelScope.launch {
            prefs.activeProfileId.collect { profileId ->
                loadProgressData(profileId.coerceAtLeast(0))
            }
        }
    }

    private fun loadProgressData(profileId: Int) {
        viewModelScope.launch {
            db.progressDao().getProgressForProfile(profileId).collect { progressList ->
                val moduleSummaries = Module.entries.map { module ->
                    val progress = progressList.find { it.moduleId == module.name }
                    ModuleProgressSummary(
                        moduleName = "${module.swahiliName} - ${module.displayName}",
                        bestScore = if (progress != null && progress.quizBestTotal > 0)
                            "${progress.quizBestScore}/${progress.quizBestTotal}" else "\u2014",
                        stars = progress?.quizStars ?: 0
                    )
                }
                val totalXp = progressList.sumOf { it.quizBestScore * 10 }
                val level = when {
                    totalXp >= 200 -> "Tai (Eagle)"
                    totalXp >= 100 -> "Kuku (Hen)"
                    totalXp >= 50 -> "Kifaranga (Chick)"
                    else -> "Yai (Egg)"
                }
                _state.update {
                    it.copy(
                        moduleProgress = moduleSummaries,
                        totalXp = totalXp,
                        level = level
                    )
                }
            }
        }
        viewModelScope.launch {
            val streak = db.streakDao().getStreak(profileId)
            _state.update { it.copy(streak = streak?.currentStreak ?: 0) }
        }
        viewModelScope.launch {
            val coinBalance = db.coinDao().getBalance(profileId)
            _state.update { it.copy(coins = coinBalance?.coins ?: 0) }
        }
    }

    fun toggleMusic(enabled: Boolean) {
        viewModelScope.launch { prefs.setMusicEnabled(enabled) }
    }

    fun toggleSfx(enabled: Boolean) {
        viewModelScope.launch { prefs.setSfxEnabled(enabled) }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ParentViewModel(application) as T
        }
    }
}
