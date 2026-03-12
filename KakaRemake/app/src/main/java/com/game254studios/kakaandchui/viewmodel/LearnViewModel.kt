package com.game254studios.kakaandchui.viewmodel

import androidx.lifecycle.ViewModel
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

    fun loadModule(module: Module) {
        _items.value = ContentRepository.getItems(module)
        _currentPage.value = 0
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }
}
