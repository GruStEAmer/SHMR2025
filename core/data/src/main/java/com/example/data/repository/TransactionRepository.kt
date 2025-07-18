package com.example.data.repository

import com.example.data.local.entity.LocalTransaction
import com.example.data.network.model.transaction.Transaction
import com.example.data.network.model.transaction.TransactionRequest
import com.example.data.network.model.transaction.TransactionResponse

interface TransactionRepository {
    suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Transaction>
    suspend fun getTransactionById(id: Int): Result<TransactionResponse>
    suspend fun putTransactionById(id: Int, transactionRequest: TransactionRequest): Result<TransactionResponse>
    suspend fun getTransactionsByAccountIdWithDate(
        accountId: Int,
        startDate: String,
        endDate: String
    ): Result<List<TransactionResponse>>
    suspend fun getUnsyncedTransactions(): List<LocalTransaction>
    suspend fun syncLocalTransactionToServer(localTransaction: LocalTransaction): Result<TransactionResponse>
    suspend fun finalizeTransactionSync(temporaryId: Int, serverResponse: Transaction): Result<Unit>
    suspend fun finalizeTransactionUpdate(serverId: Int, serverResponse: TransactionResponse): Result<Unit>
}