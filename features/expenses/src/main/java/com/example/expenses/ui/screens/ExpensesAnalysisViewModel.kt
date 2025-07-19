package com.example.expenses.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.TransactionRepository
import com.example.mapper.toTransactionUi
import com.example.model.TransactionUi
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class ExpensesAnalysisViewModel @Inject constructor(
    val repository: TransactionRepository
): ViewModel() {
    companion object {
        const val ACCOUNT_ID = 11
    }

    private val _expensesUiState = MutableStateFlow<UiState<List<TransactionUi>>>(UiState.Loading)
    val expensesUiState: StateFlow<UiState<List<TransactionUi>>> = _expensesUiState.asStateFlow()

    private val _sumExpenses = MutableStateFlow(0.0)
    val sumExpenses: StateFlow<Double> = _sumExpenses.asStateFlow()

    fun getExpenses(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _sumExpenses.value = 0.0
            _expensesUiState.value = UiState.Loading

            val data = repository.getTransactionsByAccountIdWithDate(
                ACCOUNT_ID,
                startDate = startDate.toString(),
                endDate = endDate.plusDays(1).toString()
            )

            if (data.isSuccess) {
                val filteredListIsExpenses = data.getOrNull()!!.filter{ !it.category.isIncome }.map { it.toTransactionUi() }
                _expensesUiState.value = UiState.Success(filteredListIsExpenses)
                _sumExpenses.value = filteredListIsExpenses.sumOf { it.amount.toDouble() }
            } else {
                _expensesUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }
}