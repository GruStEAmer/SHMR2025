package com.example.shmr.presentation.viewModels

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
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.repository.AccountRepository
import com.example.shmr.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckViewModel(
    private val repository: AccountRepository
): ViewModel() {

    var checkUiState: UiState<AccountResponse> by mutableStateOf(UiState.Loading)
        private set

    init {
        getAccountInfo()
    }

    fun getAccountInfo(){
        viewModelScope.launch(Dispatchers.IO){
            checkUiState = UiState.Loading

            val data = repository.getAccountById(StartAccount.ID)
            if(data.isSuccess)
                checkUiState = UiState.Success(data.getOrNull()!!)
            else
                checkUiState = UiState.Error(data.exceptionOrNull()!!)
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