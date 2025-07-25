package com.example.account.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.model.account.AccountUpdateRequest
import com.example.data.repository.AccountRepository
import com.example.data.repository.TransactionRepository
import com.example.mapper.toAccountUi
import com.example.mapper.toTransactionUi
import com.example.model.AccountUi
import com.example.ui.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.SortedMap
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val repository: AccountRepository,
    private val transactionRep: TransactionRepository
) : ViewModel() {

    companion object {
        private const val ACCOUNT_ID = 11
    }

    private val _accountUiState = MutableStateFlow<UiState<AccountUi>>(UiState.Loading)
    val accountUiState: StateFlow<UiState<AccountUi>> = _accountUiState.asStateFlow()

    private val _transactionsUiState = MutableStateFlow<List<Double>>(List<Double>(31){ 1000.0 })
    val transactionsUiState: StateFlow<List<Double>> = _transactionsUiState.asStateFlow()

    init {
        loadAccountData()
        getTransactions()
    }

    fun loadAccountData() {
        viewModelScope.launch(Dispatchers.IO) {
            _accountUiState.value = UiState.Loading
            val result = repository.getAccountById(ACCOUNT_ID)
            if (result.isSuccess) {
                _accountUiState.value = UiState.Success(result.getOrThrow().toAccountUi())
            } else {
                _accountUiState.value = UiState.Error(result.exceptionOrNull() ?: Exception("Неизвестная ошибка загрузки счета"))
            }
        }
    }

    fun updateAccount(name: String, balance: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val currentData = _accountUiState.value
            val currentCurrency = (currentData as? UiState.Success<AccountUi>)?.data?.currency ?: "RUB"
            val accountRequest = AccountUpdateRequest(name, balance, currentCurrency)

            val putResult = repository.putAccountById(ACCOUNT_ID, accountRequest)

            if (putResult.isSuccess) {
                val dataResult = repository.getAccountById(ACCOUNT_ID)
                if (dataResult.isSuccess) {
                    _accountUiState.value = UiState.Success(dataResult.getOrThrow().toAccountUi())
                } else {
                    _accountUiState.value = UiState.Error(dataResult.exceptionOrNull() ?: Exception("Ошибка загрузки данных после сохранения"))
                }
            } else {
               _accountUiState.value = UiState.Error(
                    putResult.exceptionOrNull() ?: Exception("Неизвестная ошибка при сохранении изменений")
                )
                if(currentData is UiState.Success) {
                    _accountUiState.value = currentData
                 }
            }
        }
    }
    fun getTransactions(
        startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
        endDate: LocalDate = LocalDate.now()
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val data = transactionRep.getTransactionsByAccountIdWithDate(
                accountId = ACCOUNT_ID,
                startDate = startDate.toString(),
                endDate = endDate.plusDays(1).toString()
            )

            if (data.isSuccess) {
                val transactions = data.getOrNull()!!.map { it.toTransactionUi() }
                val hm: SortedMap<String, Double> = HashMap<String, Double>().toSortedMap()
                for(it in transactions){
                    val str = it.date.toString().substring(8..9)
                    hm[str] = hm.getOrDefault(str, 0.0) +
                            if(it.isIncome) it.amount.toDouble() else it.amount.toDouble() * (-1.0)
                }
                val list = MutableList<Double>(31) {
                    0.0
                }
                hm.forEach { it->
                    list[it.key.toInt()] = it.value
                }
                _transactionsUiState.value = list
            }

        }
    }
}