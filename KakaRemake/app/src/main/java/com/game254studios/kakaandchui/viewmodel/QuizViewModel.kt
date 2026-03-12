package com.game254studios.kakaandchui.viewmodel

import androidx.lifecycle.ViewModel
import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizState(
    val currentIndex: Int = 0,
    val score: Int = 0,
    val answered: Boolean = false,
    val selectedAnswer: String? = null,
    val correctAnswer: String = "",
    val options: List<LearningItem> = emptyList(),
    val currentItem: LearningItem? = null,
    val allItems: List<LearningItem> = emptyList(),
    val isFinished: Boolean = false
)

class QuizViewModel : ViewModel() {
    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private var shuffledItems: List<LearningItem> = emptyList()
    private var allModuleItems: List<LearningItem> = emptyList()

    fun loadModule(module: Module) {
        allModuleItems = ContentRepository.getItems(module)
        shuffledItems = allModuleItems.shuffled()
        loadQuestion(0)
    }

    private fun loadQuestion(index: Int) {
        if (index >= shuffledItems.size) {
            _state.value = _state.value.copy(isFinished = true)
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
