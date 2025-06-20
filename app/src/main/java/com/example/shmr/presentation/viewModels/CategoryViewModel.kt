package com.example.shmr.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.MainApplication
import com.example.shmr.domain.model.category.Category
import com.example.shmr.domain.repository.CategoryRepository
import com.example.shmr.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository
): ViewModel() {

    var categoryUiState: UiState<List<Category>> by mutableStateOf(UiState.Loading)
        private set
    init {
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getCategories()
            if(data.isSuccess)
                categoryUiState = UiState.Success(data.getOrNull()!!)
            else
                categoryUiState = UiState.Error(data.exceptionOrNull()!!)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val categoryRepository = application.container.categoryRepository
                CategoryViewModel(repository = categoryRepository)
            }
        }
    }
}