package com.example.account.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.account.domain.repository.AccountRepository
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountResponse
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    private val _checkUiState = MutableStateFlow<UiState<AccountResponse>>(UiState.Loading)
    val checkUiState: StateFlow<UiState<AccountResponse>> = _checkUiState.asStateFlow()

    init {
        getAccountInfo()
    }

    fun getAccountInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _checkUiState.value = UiState.Loading
            val data = repository.getAccountById(11)
            if (data.isSuccess) {
                _checkUiState.value = UiState.Success(data.getOrNull()!!)
            } else {
                _checkUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }

    fun putAccount(name: String, balance: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val accountRequest = AccountCreateRequest(name, balance, "RUB")
            val data = repository.putAccountById(accountCreateRequest = accountRequest)

            getAccountInfo()
        }
    }
}