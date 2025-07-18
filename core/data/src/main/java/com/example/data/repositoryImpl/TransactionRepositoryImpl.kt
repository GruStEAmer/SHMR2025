package com.example.data.repositoryImpl

import com.example.data.network.apiService.TransactionApiService
import com.example.data.network.model.transaction.TransactionRequest
import com.example.data.network.model.transaction.TransactionResponse
import com.example.data.repository.TransactionRepository
import java.net.UnknownHostException
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApiService: TransactionApiService
): TransactionRepository {

        override suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Unit> {
            return try {
                val response = transactionApiService.postTransaction(transactionRequest)
                when (response.code()) {
                    200, 201 -> Result.success(Unit)
                    else -> Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: UnknownHostException) {
                Result.failure(Exception("Нет подключения к интернету"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка при создании транзакции: ${e.localizedMessage}"))
            }
        }

        override suspend fun putTransactionById(
            id: Int,
            transactionRequest: TransactionRequest
        ): Result<Unit> {
            return try {
                val response = transactionApiService.putTransactionById(id, transactionRequest)
                when (response.code()) {
                    200, 204 -> Result.success(Unit)
                    else -> Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: UnknownHostException) {
                Result.failure(Exception("Нет подключения к интернету"))
            } catch (e: Exception) {
                Result.failure(Exception("Ошибка при обновлении транзакции: ${e.localizedMessage}"))
            }
        }


        override suspend fun getTransactionById(id: Int): Result<TransactionResponse> {
        return try {
            val response = transactionApiService.getTransactionById(id)
            when(response.code()){
                200 -> Result.success(response.body()!!)
                else -> Result.failure(Exception("${response.code()}"))
            }
        } catch(e : UnknownHostException){
            Result.failure(Exception("Нет подключения к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("$e"))
        }
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
                else -> Result.failure(Exception("${response.code()}"))
            }
        } catch(e : UnknownHostException){
            Result.failure(Exception("Нет подключения к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("$e"))
        }
    }
}