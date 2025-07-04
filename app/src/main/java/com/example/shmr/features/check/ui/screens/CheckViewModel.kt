package com.example.shmr.features.check.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.MainApplication
import com.example.shmr.StartAccount
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckViewModel(
    private val repository: AccountRepository
): ViewModel() {


    private val _checkUiState = MutableStateFlow<UiState<AccountResponse>>(UiState.Loading)
    val checkUiState: StateFlow<UiState<AccountResponse>> = _checkUiState.asStateFlow()

    init {
        getAccountInfo()
    }

    fun getAccountInfo(){
        viewModelScope.launch(Dispatchers.IO){
            _checkUiState.value = UiState.Loading

            val data = repository.getAccountById(StartAccount.ID)
            if(data.isSuccess)
                _checkUiState.value = UiState.Success(data.getOrNull()!!)
            else
                _checkUiState.value = UiState.Error(data.exceptionOrNull()!!)
        }
    }

    companion object{
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val repository = application.container.accountRepository
                CheckViewModel(repository = repository)
            }
        }
    }
}