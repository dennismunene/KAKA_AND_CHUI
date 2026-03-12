package com.game254studios.kakaandchui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.data.repository.ContentRepository
import com.game254studios.kakaandchui.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SoundMatchState(
    val currentIndex: Int = 0,
    val score: Int = 0,
    val totalItems: Int = 0,
    val currentItem: LearningItem? = null,
    val options: List<LearningItem> = emptyList(),
    val answered: Boolean = false,
    val selectedAnswer: String? = null,
    val isCorrect: Boolean = false,
    val isFinished: Boolean = false,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val showCelebration: Boolean = false
)

class SoundMatchViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(SoundMatchState())
    val state: StateFlow<SoundMatchState> = _state.asStateFlow()

    private val db = KakaDatabase.getInstance(application)
    private val prefs = UserPreferences(application)
    private val gameRepo = GameRepository(db, prefs)

    private var shuffledItems: List<LearningItem> = emptyList()
    private var allModuleItems: List<LearningItem> = emptyList()
    private var currentModule: Module? = null

    fun loadModule(module: Module) {
        currentModule = module
        allModuleItems = ContentRepository.getItems(module)
        shuffledItems = allModuleItems.shuffled()

        _state.value = SoundMatchState(totalItems = shuffledItems.size)
        loadRound(0)
    }

    private fun loadRound(index: Int) {
        if (index >= shuffledItems.size) {
            finishGame()
            return
        }
        val target = shuffledItems[index]
        val distractors = allModuleItems
            .filter { it.id != target.id }
            .shuffled()
            .take(3)
        val options = (distractors + target).shuffled()

        _state.value = _state.value.copy(
            currentIndex = index,
            currentItem = target,
            options = options,
            answered = false,
            selectedAnswer = null,
            isCorrect = false,
            showCelebration = false
        )
    }

    fun selectAnswer(itemId: String) {
        val current = _state.value
        if (current.answered) return

        val correct = itemId == current.currentItem?.id
        _state.value = current.copy(
            answered = true,
            selectedAnswer = itemId,
            isCorrect = correct,
            score = if (correct) current.score + 1 else current.score,
            showCelebration = correct
        )
    }

    fun nextRound() {
        loadRound(_state.value.currentIndex + 1)
    }

    private fun finishGame() {
        val module = currentModule ?: return
        val score = _state.value.score
        val total = shuffledItems.size

        viewModelScope.launch {
            val profile = gameRepo.getOrCreateDefaultProfile()
            val reward = gameRepo.saveQuizResult(profile.id, module.name, score, total)

            _state.value = _state.value.copy(
                isFinished = true,
                xpEarned = reward.xpEarned,
                coinsEarned = reward.coinsEarned,
                showCelebration = true
            )
        }
    }
}
