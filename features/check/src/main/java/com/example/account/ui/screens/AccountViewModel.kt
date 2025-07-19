package com.example.account.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.model.account.AccountUpdateRequest
import com.example.data.repository.AccountRepository
import com.example.mapper.toAccountUi
import com.example.model.AccountUi
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

    companion object {
        private const val ACCOUNT_ID = 11
    }

    private val _accountUiState = MutableStateFlow<UiState<AccountUi>>(UiState.Loading)
    val accountUiState: StateFlow<UiState<AccountUi>> = _accountUiState.asStateFlow()

    init {
        loadAccountData()
    }

    fun loadAccountData() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountUiState.value = UiState.Loading
            val result = repository.getAccountById(ACCOUNT_ID)
            if (result.isSuccess) {
                _accountUiState.value = UiState.Success(result.getOrThrow().toAccountUi())
            } else {
                _accountUiState.value = UiState.Error(result.exceptionOrNull() ?: Exception("Неизвестная ошибка загрузки счета"))
            }
        }
    }

    fun refreshAccountData() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountUiState.value = UiState.Loading
            val refreshResult = repository.refreshAccountById(ACCOUNT_ID)
            if (refreshResult.isSuccess) {
                val dataResult = repository.getAccountById(ACCOUNT_ID)
                if (dataResult.isSuccess) {
                    _accountUiState.value = UiState.Success(dataResult.getOrThrow().toAccountUi())
                } else {
                    _accountUiState.value = UiState.Error(dataResult.exceptionOrNull() ?: Exception("Ошибка загрузки данных после обновления"))
                }
            } else {
                 _accountUiState.value = UiState.Error(
                    refreshResult.exceptionOrNull() ?: Exception("Неизвестная ошибка при обновлении данных")
                )
            }
        }
    }

    fun updateAccount(name: String, balance: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val currentData = _accountUiState.value
            val currentCurrency = (currentData as? UiState.Success<AccountUi>)?.data?.currency ?: "RUB"
            val accountRequest = AccountUpdateRequest(name, balance, currentCurrency)

            val putResult = repository.putAccountById(ACCOUNT_ID, accountRequest)

            if (putResult.isSuccess) {
                val dataResult = repository.getAccountById(ACCOUNT_ID)
                if (dataResult.isSuccess) {
                    _accountUiState.value = UiState.Success(dataResult.getOrThrow().toAccountUi())
                } else {
                    _accountUiState.value = UiState.Error(dataResult.exceptionOrNull() ?: Exception("Ошибка загрузки данных после сохранения"))
                }
            } else {
               _accountUiState.value = UiState.Error(
                    putResult.exceptionOrNull() ?: Exception("Неизвестная ошибка при сохранении изменений")
                )
                if(currentData is UiState.Success) {
                    _accountUiState.value = currentData
                 }
            }
        }
    }
}