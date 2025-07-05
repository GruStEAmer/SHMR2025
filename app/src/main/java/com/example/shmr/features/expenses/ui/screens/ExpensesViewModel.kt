package com.example.shmr.features.expenses.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.MainApplication
import com.example.shmr.StartAccount
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.transaction.TransactionResponse
import com.example.shmr.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExpensesViewModel(
    val repository: TransactionRepository
): ViewModel() {

    private val _expensesUiState = MutableStateFlow<UiState<List<TransactionResponse>>>(UiState.Loading)
    val expensesUiState: StateFlow<UiState<List<TransactionResponse>>> = _expensesUiState.asStateFlow()

    private val _sumExpenses = MutableStateFlow(0.0)
    val sumExpenses: StateFlow<Double> = _sumExpenses.asStateFlow()

    init {
        getTransactions()
    }

    fun getTransactions(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _expensesUiState.value = UiState.Loading
            _sumExpenses.value = 0.0

            val data = repository.getTransactionByAccountIdWithDate(
                accountId = StartAccount.ID,
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )

            if (data.isSuccess) {
                val transactions = data.getOrNull()!!.filter { !it.category.isIncome }
                _expensesUiState.value = UiState.Success(transactions)
                _sumExpenses.value = transactions.sumOf { it.amount.toDouble() }
            } else {
                _expensesUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY]) as MainApplication
                val repository = application.container.transactionRepository
                ExpensesViewModel(repository = repository)
            }
        }
    }
}