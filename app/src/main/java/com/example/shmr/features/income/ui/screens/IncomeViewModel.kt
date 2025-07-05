package com.example.shmr.features.income.ui.screens

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.StartAccount
import com.example.shmr.MainApplication
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.transaction.TransactionResponse
import com.example.shmr.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class IncomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _incomeUiState = MutableStateFlow<UiState<List<TransactionResponse>>>(UiState.Loading)
    val incomeUiState: StateFlow<UiState<List<TransactionResponse>>> = _incomeUiState.asStateFlow()

    private val _sumIncome = MutableStateFlow(0.0)
    val sumIncome: StateFlow<Double> = _sumIncome.asStateFlow()

    init {
        getIncomes()
    }

    fun getIncomes(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _sumIncome.value = 0.0
            _incomeUiState.value = UiState.Loading

            val data = repository.getTransactionByAccountIdWithDate(
                accountId = StartAccount.ID,
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )

            if (data.isSuccess) {
                val filteredListIsIncome = data.getOrNull()!!.filter { it.category.isIncome }
                _incomeUiState.value = UiState.Success(filteredListIsIncome)
                _sumIncome.value = filteredListIsIncome.sumOf { it.amount.toDouble() }
            } else {
                _incomeUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY]) as MainApplication
                val repository = application.container.transactionRepository
                IncomeViewModel(repository = repository)
            }
        }
    }
}