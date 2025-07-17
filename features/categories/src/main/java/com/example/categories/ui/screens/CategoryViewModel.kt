package com.example.categories.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.categories.domain.repository.CategoryRepository
import com.example.network.model.category.Category
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
): ViewModel() {

    private val _categoryUiState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val categoryUiState: StateFlow<UiState<List<Category>>> = _categoryUiState.asStateFlow()

    private val _targetCategoryUiState = MutableStateFlow<List<Category>>(emptyList())
    val targetCategoryUiState: StateFlow<List<Category>> = _targetCategoryUiState.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryUiState.value = UiState.Loading
            val data = repository.getCategories()
            if (data.isSuccess) {
                _categoryUiState.value = UiState.Success(data.getOrNull()!!)
                _targetCategoryUiState.value = data.getOrNull()!!
            } else {
                _categoryUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }

    fun searchCategory(s: String = "") {
        val currentCategories = (_categoryUiState.value as? UiState.Success)?.data ?: return
        _targetCategoryUiState.value = currentCategories.filter { category ->
            category.name.contains(s, ignoreCase = true)
        }
    }
}