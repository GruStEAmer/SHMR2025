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
import com.example.shmr.StartAccount
import com.example.shmr.MainApplication
import com.example.shmr.domain.model.transaction.TransactionResponse
import com.example.shmr.domain.repository.TransactionRepository
import com.example.shmr.presentation.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class IncomeViewModel(
    private val repository: TransactionRepository
): ViewModel() {

    var incomeUiState: UiState<List<TransactionResponse>> by mutableStateOf(UiState.Loading)
        private set
    var sumIncome by mutableDoubleStateOf(0.0)

    init {
        getIncomes()
    }

    fun getIncomes(
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now(),
    ){

        viewModelScope.launch(Dispatchers.IO) {
            sumIncome = 0.0
            val data = repository.getTransactionByAccountIdWithDate(
                accountId = StartAccount.ID,
                startDate = startDate.toString(),
                endDate = endDate.toString()
            )
            if(data.isSuccess) {
                val filteredListIsIncome = data.getOrNull()!!.filter { it.category.isIncome }
                incomeUiState = UiState.Success(filteredListIsIncome)
                sumIncome = filteredListIsIncome.sumOf { it.amount.toDouble() }
            }
            else
                UiState.Error(data.exceptionOrNull()!!)
        }
    }


    companion object{
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY]) as MainApplication
                val repository = application.container.transactionRepository
                IncomeViewModel(repository = repository)
            }
        }
    }
}
