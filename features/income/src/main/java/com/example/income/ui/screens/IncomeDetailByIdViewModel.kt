package com.example.income.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.income.data.model.TransactionRequest
import com.example.income.domain.repository.IncomeRepository
import com.example.income.ui.model.TransactionResponseUi
import com.example.income.ui.model.toTransactionResponseUi
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IncomeDetailByIdViewModel @Inject constructor(
    val repository: IncomeRepository
): ViewModel() {
    private val _transaction = MutableStateFlow<UiState<TransactionResponseUi>>(UiState.Loading)
    val transaction:StateFlow<UiState<TransactionResponseUi>> = _transaction.asStateFlow()

    fun getTransactionById(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            val data = repository.getTransactionById(id)
            if(data.isSuccess){
                _transaction.value = UiState.Success(data.getOrNull()!!.toTransactionResponseUi())
            }
            else {
                _transaction.value = UiState.Error(data.exceptionOrNull()!!)
            }
        }
    }
    fun putTransactionById(id: Int, transactionRequest: TransactionRequest){
        viewModelScope.launch(Dispatchers.IO){
            val data = repository.putTransactionById(id, transactionRequest)

            if(data.isFailure){
                Log.e("error", "${data.exceptionOrNull()}")
            }
        }
    }
}