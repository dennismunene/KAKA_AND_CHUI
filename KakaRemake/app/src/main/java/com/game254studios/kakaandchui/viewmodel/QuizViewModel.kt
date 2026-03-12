package com.game254studios.kakaandchui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.game254studios.kakaandchui.analytics.AnalyticsManager
import com.game254studios.kakaandchui.data.local.KakaDatabase
import com.game254studios.kakaandchui.data.local.UserPreferences
import com.game254studios.kakaandchui.data.model.AchievementDef
import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.data.repository.ContentRepository
import com.game254studios.kakaandchui.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizState(
    val currentIndex: Int = 0,
    val score: Int = 0,
    val answered: Boolean = false,
    val selectedAnswer: String? = null,
    val correctAnswer: String = "",
    val options: List<LearningItem> = emptyList(),
    val currentItem: LearningItem? = null,
    val allItems: List<LearningItem> = emptyList(),
    val isFinished: Boolean = false,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val newAchievements: List<AchievementDef> = emptyList()
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

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
        _state.value = QuizState()
        loadQuestion(0)
    }

    private fun loadQuestion(index: Int) {
        if (index >= shuffledItems.size) {
            finishQuiz()
            return
        }
        val currentItem = shuffledItems[index]
        val wrongOptions = allModuleItems.filter { it.id != currentItem.id }.shuffled()
        val numOptions = minOf(3, wrongOptions.size)
        val options = (wrongOptions.take(numOptions) + currentItem).shuffled()

        _state.value = _state.value.copy(
            currentIndex = index,
            answered = false,
            selectedAnswer = null,
            correctAnswer = currentItem.id,
            options = options,
            currentItem = currentItem,
            allItems = shuffledItems,
            isFinished = false
        )
    }

    private fun finishQuiz() {
        val score = _state.value.score
        val total = shuffledItems.size
        val module = currentModule ?: return

        viewModelScope.launch {
            val profile = gameRepo.getOrCreateDefaultProfile()
            val reward = gameRepo.saveQuizResult(profile.id, module.name, score, total)

            val stars = when {
                total == 0 -> 0
                score == total -> 3
                score >= total * 2 / 3 -> 2
                score >= total / 3 -> 1
                else -> 0
            }
            AnalyticsManager.getInstance(getApplication()).logQuizCompleted(
                moduleName = module.name,
                score = score,
                total = total,
                stars = stars
            )

            _state.value = _state.value.copy(
                isFinished = true,
                xpEarned = reward.xpEarned,
                coinsEarned = reward.coinsEarned,
                newAchievements = reward.newAchievements
            )
        }
    }

    fun selectAnswer(itemId: String) {
        val current = _state.value
        if (current.answered) return
        val isCorrect = itemId == current.correctAnswer
        _state.value = current.copy(
            answered = true,
            selectedAnswer = itemId,
            score = if (isCorrect) current.score + 1 else current.score
        )
    }

    fun nextQuestion() {
        loadQuestion(_state.value.currentIndex + 1)
    }
}
