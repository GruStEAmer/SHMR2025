package com.example.expenses.domain.repository

import com.example.expenses.data.model.TransactionRequest
import com.example.expenses.data.model.TransactionResponse

interface ExpensesRepository {

    suspend fun postTransaction(transactionRequest: TransactionRequest) : Result<Unit>

    suspend fun putTransactionById(
        id: Int,
        transactionRequest: TransactionRequest
    ): Result<Unit>

    suspend fun getTransactionById(
        id: Int
    ): Result<TransactionResponse>
    suspend fun getTransactionByAccountIdWithDate(
        accountId: Int = 11,
        startDate:  String,
        endDate: String
    ) : Result<List<TransactionResponse>>

}