package com.example.shmr.data.repository

import com.example.shmr.data.remote.api.TransactionApiService
import com.example.shmr.domain.model.transaction.Transaction
import com.example.shmr.domain.model.transaction.TransactionRequest
import com.example.shmr.domain.model.transaction.TransactionResponse
import com.example.shmr.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionApiService: TransactionApiService
) : TransactionRepository
{
    override suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionRepository> {
        TODO("Not yet implemented")
    }

    override suspend fun putTransactionById(
        id: Int,
        transactionRequest: TransactionRequest
    ): Result<TransactionResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransactionById(id: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionByAccountIdWithDate(
        accountId: Int,
        startDate: String,
        endDate: String
    ): Result<List<TransactionResponse>> {
        return try {
            val response = transactionApiService.getTransactionsByAccountIdWithDate(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            when(response.code()){
                200 -> Result.success(response.body()!!)
                else -> Result.failure(Exception("Network Transaction Error ${response.code()}: ${response.body()}"))
            }
        } catch(e : Exception){
            Result.failure(Exception("Transaction Error $e"))
        }
    }
}