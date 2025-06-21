package com.example.shmr.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shmr.MainApplication
import com.example.shmr.StartAccount
import com.example.shmr.domain.model.transaction.TransactionResponse
import com.example.shmr.domain.repository.TransactionRepository
import com.example.shmr.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExpensesViewModel(
    val repository: TransactionRepository
): ViewModel() {

    var expensesUiState: UiState<List<TransactionResponse>> by mutableStateOf(UiState.Loading)
        private set
    var sumExpenses by mutableDoubleStateOf(0.0)
        private set

    init {
        getTransactions()
    }

    fun getTransactions(
        startDate: LocalDate  = LocalDate.now(),
        endDate: LocalDate = LocalDate.now()
    ) {
        viewModelScope.launch(Dispatchers.IO){
            expensesUiState = UiState.Loading
            sumExpenses = 0.0

            val data = repository.getTransactionByAccountIdWithDate(
                accountId = StartAccount.ID,
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )
            if(data.isSuccess)   {
                val transaction = data.getOrNull()!!.filter { !it.category.isIncome }
                expensesUiState = UiState.Success(transaction)
                sumExpenses = transaction.sumOf { it.amount.toDouble() }
            }
            else
                expensesUiState = UiState.Error(data.exceptionOrNull()!!)
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