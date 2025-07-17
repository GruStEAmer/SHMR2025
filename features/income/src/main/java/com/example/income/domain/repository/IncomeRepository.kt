package com.example.income.domain.repository

import com.example.income.ui.model.TransactionUi
import com.example.network.model.transaction.TransactionRequest

interface IncomeRepository {

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