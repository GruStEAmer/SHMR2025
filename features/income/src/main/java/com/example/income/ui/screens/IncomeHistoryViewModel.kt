package com.example.income.ui.screens

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

class IncomeHistoryViewModel @Inject constructor(
    val repository: TransactionRepository
): ViewModel() {

    private val _incomeUiState = MutableStateFlow<UiState<List<TransactionUi>>>(UiState.Loading)
    val incomeUiState: StateFlow<UiState<List<TransactionUi>>> = _incomeUiState.asStateFlow()

    private val _sumIncome = MutableStateFlow(0.0)
    val sumIncome: StateFlow<Double> = _sumIncome.asStateFlow()

    fun getIncomes(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _sumIncome.value = 0.0
            _incomeUiState.value = UiState.Loading

            val data = repository.getTransactionByAccountIdWithDate(
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )

            if (data.isSuccess) {
                val filteredListIsIncome = data.getOrNull()?.map { it.toTransactionUi() } ?: emptyList()
                _incomeUiState.value = UiState.Success(filteredListIsIncome)
                _sumIncome.value = filteredListIsIncome.sumOf { it.amount.toDouble() }
            } else {
                _incomeUiState.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }
}