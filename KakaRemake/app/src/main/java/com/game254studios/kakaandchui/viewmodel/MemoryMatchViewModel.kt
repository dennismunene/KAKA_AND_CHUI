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

data class MemoryCard(
    val id: Int,
    val item: LearningItem,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
)

data class MemoryMatchState(
    val cards: List<MemoryCard> = emptyList(),
    val firstFlipped: Int? = null,
    val secondFlipped: Int? = null,
    val matchesFound: Int = 0,
    val totalPairs: Int = 0,
    val isChecking: Boolean = false,
    val isFinished: Boolean = false,
    val xpEarned: Int = 0,
    val coinsEarned: Int = 0,
    val showCelebration: Boolean = false,
    val lastMatchedAudio: String? = null
)

class MemoryMatchViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MemoryMatchState())
    val state: StateFlow<MemoryMatchState> = _state.asStateFlow()

    private val db = KakaDatabase.getInstance(application)
    private val prefs = UserPreferences(application)
    private val gameRepo = GameRepository(db, prefs)

    private var currentModule: Module? = null

    fun loadModule(module: Module) {
        currentModule = module
        val allItems = ContentRepository.getItems(module)
        val pairCount = minOf(6, allItems.size)
        val selectedItems = allItems.shuffled().take(pairCount)

        // Create two cards for each item
        val cards = selectedItems.flatMapIndexed { index, item ->
            listOf(
                MemoryCard(id = index * 2, item = item),
                MemoryCard(id = index * 2 + 1, item = item)
            )
        }.shuffled()

        _state.value = MemoryMatchState(
            cards = cards,
            totalPairs = pairCount
        )
    }

    fun flipCard(cardId: Int) {
        val current = _state.value
        if (current.isChecking || current.isFinished) return

        val card = current.cards.find { it.id == cardId } ?: return
        if (card.isFlipped || card.isMatched) return

        val updatedCards = current.cards.map {
            if (it.id == cardId) it.copy(isFlipped = true) else it
        }

        if (current.firstFlipped == null) {
            _state.value = current.copy(
                cards = updatedCards,
                firstFlipped = cardId,
                lastMatchedAudio = card.item.audioAsset
            )
        } else {
            _state.value = current.copy(
                cards = updatedCards,
                secondFlipped = cardId,
                isChecking = true,
                lastMatchedAudio = card.item.audioAsset
            )
        }
    }

    fun checkMatch() {
        val current = _state.value
        val firstId = current.firstFlipped ?: return
        val secondId = current.secondFlipped ?: return

        val firstCard = current.cards.find { it.id == firstId } ?: return
        val secondCard = current.cards.find { it.id == secondId } ?: return

        val isMatch = firstCard.item.id == secondCard.item.id

        if (isMatch) {
            val newMatches = current.matchesFound + 1
            val updatedCards = current.cards.map {
                if (it.id == firstId || it.id == secondId)
                    it.copy(isMatched = true, isFlipped = true)
                else it
            }
            val finished = newMatches == current.totalPairs

            _state.value = current.copy(
                cards = updatedCards,
                firstFlipped = null,
                secondFlipped = null,
                matchesFound = newMatches,
                isChecking = false,
                showCelebration = true,
                isFinished = finished
            )

            if (finished) {
                finishGame()
            }
        } else {
            val updatedCards = current.cards.map {
                if (it.id == firstId || it.id == secondId)
                    it.copy(isFlipped = false)
                else it
            }
            _state.value = current.copy(
                cards = updatedCards,
                firstFlipped = null,
                secondFlipped = null,
                isChecking = false,
                showCelebration = false
            )
        }
    }

    fun dismissCelebration() {
        _state.value = _state.value.copy(showCelebration = false)
    }

    private fun finishGame() {
        val module = currentModule ?: return
        val matches = _state.value.matchesFound
        val xp = matches * 5
        val coins = matches * 3

        viewModelScope.launch {
            val profile = gameRepo.getOrCreateDefaultProfile()
            gameRepo.addXp(profile.id, xp)
            gameRepo.addCoins(profile.id, coins)
            gameRepo.updateStreak(profile.id)

            _state.value = _state.value.copy(
                xpEarned = xp,
                coinsEarned = coins,
                showCelebration = true
            )
        }
    }
}
