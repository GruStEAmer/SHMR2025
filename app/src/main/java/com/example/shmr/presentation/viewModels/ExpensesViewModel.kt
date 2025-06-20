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

    init {
        getTransactions()
    }

    fun getTransactions(
        startDate:String  = LocalDate.now().toString(),
        endDate: String = LocalDate.now().toString()
    ) {
        viewModelScope.launch(Dispatchers.IO){
            expensesUiState = UiState.Loading

            val data = repository.getTransactionByAccountIdWithDate(
                accountId = StartAccount.ID,
                startDate = startDate,
                endDate = endDate
            )
            expensesUiState = if(data.isSuccess)
                UiState.Success(data.getOrNull()!!.filter { !it.category.isIncome })
            else
                UiState.Error(data.exceptionOrNull()!!)
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