package com.example.income.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.TransactionRepository
import com.example.mapper.toTransactionUi
import com.example.model.CategoryUi
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class IncomeAnalysisViewModel @Inject constructor(
    val repository: TransactionRepository
): ViewModel() {
    companion object {
        const val ACCOUNT_ID = 11
    }

    private val _incomeUiState = MutableStateFlow<UiState<List<Pair<CategoryUi, Double>>>>(UiState.Loading)
    val incomeUiState: StateFlow<UiState<List<Pair<CategoryUi, Double>>>> = _incomeUiState.asStateFlow()

    private val _sumIncome = MutableStateFlow(0.0)
    val sumIncome: StateFlow<Double> = _sumIncome.asStateFlow()

    fun getIncomes(
        accountId: Int = ACCOUNT_ID,
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _sumIncome.value = 0.0
            _incomeUiState.value = UiState.Loading

            val dataResult = repository.getTransactionsByAccountIdWithDate(
                accountId,
                startDate = startDate.toString(),
                endDate = endDate.plusDays(1).toString()
            )

            dataResult.fold(
                onSuccess = { networkTransactions ->
                    val expenseTransactionsUi = networkTransactions
                        .filter { it.category.isIncome }
                        .map{ it.toTransactionUi() }

                    _sumIncome.value = expenseTransactionsUi.sumOf {
                        it.amount.toDoubleOrNull() ?: 0.0
                    }

                    val incomeByCategory = HashMap<CategoryUi, Double>()
                    expenseTransactionsUi.forEach { transaction ->
                        val category = CategoryUi(
                            id = transaction.categoryId,
                            name = transaction.categoryName,
                            emoji = transaction.categoryEmoji,
                            isIncome = transaction.isIncome
                        )
                        val amount = transaction.amount.toDoubleOrNull() ?: 0.0

                        incomeByCategory[category] = (incomeByCategory[category] ?: 0.0) + amount
                    }

                    _incomeUiState.value = UiState.Success(incomeByCategory.toList())

                },
                onFailure = { exception ->
                    _incomeUiState.value = UiState.Error(exception)
                    Log.e("incomeVM", "Error fetching income: ${exception.message}", exception)
                }
            )
        }
    }
}