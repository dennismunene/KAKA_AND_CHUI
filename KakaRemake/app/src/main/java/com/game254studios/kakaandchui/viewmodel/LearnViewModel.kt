package com.game254studios.kakaandchui.viewmodel

import androidx.lifecycle.ViewModel
import com.game254studios.kakaandchui.analytics.AnalyticsManager
import com.game254studios.kakaandchui.data.model.LearningItem
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LearnViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<LearningItem>>(emptyList())
    val items: StateFlow<List<LearningItem>> = _items.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private var currentModule: Module? = null
    private var analyticsManager: AnalyticsManager? = null

    fun setAnalyticsManager(manager: AnalyticsManager) {
        analyticsManager = manager
    }

    fun loadModule(module: Module) {
        currentModule = module
        _items.value = ContentRepository.getItems(module)
        _currentPage.value = 0
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }

    fun logSession() {
        val module = currentModule ?: return
        val itemsViewed = _currentPage.value + 1
        analyticsManager?.logLearnSession(
            moduleName = module.name,
            itemsViewed = itemsViewed
        )
    }
}
