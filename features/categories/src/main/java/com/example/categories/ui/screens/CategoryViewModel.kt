package com.example.categories.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.CategoryRepository
import com.example.mapper.toCategoryUi
import com.example.model.CategoryUi
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

    private val _categoryUiState = MutableStateFlow<UiState<List<CategoryUi>>>(UiState.Loading)
    val categoryUiState: StateFlow<UiState<List<CategoryUi>>> = _categoryUiState.asStateFlow()

    private val _targetCategoryUiState = MutableStateFlow<List<CategoryUi>>(emptyList())
    val targetCategoryUiState: StateFlow<List<CategoryUi>> = _targetCategoryUiState.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryUiState.value = UiState.Loading
            val data = repository.getCategories()
            if (data.isSuccess) {
                _targetCategoryUiState.value = data.getOrNull()!!.map{ it.toCategoryUi() }
                _categoryUiState.value = UiState.Success(_targetCategoryUiState.value)
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