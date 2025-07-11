package com.example.expenses.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenses.data.model.TransactionResponse
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    val repository: ExpensesRepository
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
}