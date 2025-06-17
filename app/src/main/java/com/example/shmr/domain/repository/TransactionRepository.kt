package com.example.shmr.domain.repository

import com.example.shmr.domain.model.transaction.Transaction
import com.example.shmr.domain.model.transaction.TransactionRequest
import com.example.shmr.domain.model.transaction.TransactionResponse

interface TransactionRepository {
    suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Transaction>

    suspend fun getTransactionById(id: Int): Result<TransactionRepository>

    suspend fun putTransactionById(id: Int, transactionRequest: TransactionRequest): Result<TransactionResponse>

    suspend fun deleteTransactionById(id: Int) : Result<Unit>

    suspend fun getTransactionByAccountId(
        accountId: Int,
        startDate:  String,
        endDate: String
    ) : Result<List<TransactionResponse>>
}