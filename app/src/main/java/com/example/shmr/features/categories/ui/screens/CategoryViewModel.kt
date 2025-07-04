package com.example.shmr.features.categories.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.MainApplication
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.category.Category
import com.example.shmr.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val categoryRepository = application.container.categoryRepository
                CategoryViewModel(repository = categoryRepository)
            }
        }
    }
}