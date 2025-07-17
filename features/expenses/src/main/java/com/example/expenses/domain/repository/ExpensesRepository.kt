package com.example.expenses.domain.repository

import com.example.expenses.ui.model.TransactionUi
import com.example.network.model.transaction.TransactionRequest
import com.example.network.model.transaction.TransactionResponse

interface ExpensesRepository {

    suspend fun postTransaction(transactionRequest: TransactionRequest) : Result<Unit>

    suspend fun putTransactionById(
        id: Int,
        transactionRequest: TransactionRequest
    ): Result<Unit>

    suspend fun getTransactionById(
        id: Int
    ): Result<TransactionUi>
    suspend fun getTransactionByAccountIdWithDate(
        accountId: Int = 11,
        startDate:  String,
        endDate: String
    ) : Result<List<TransactionUi>>

}