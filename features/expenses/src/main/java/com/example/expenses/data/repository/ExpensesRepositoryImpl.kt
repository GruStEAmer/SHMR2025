package com.example.expenses.data.repository

import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.ui.mapper.toTransactionUi
import com.example.expenses.ui.model.TransactionUi
import com.example.network.apiService.TransactionApiService
import com.example.network.model.transaction.TransactionRequest
import com.example.network.model.transaction.TransactionResponse
import java.net.UnknownHostException
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApiService: TransactionApiService
): ExpensesRepository {

        override suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Unit> {
            return try {
                val response = expensesApiService.postTransaction(transactionRequest)
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
                val response = expensesApiService.putTransactionById(id, transactionRequest)
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


        override suspend fun getTransactionById(id: Int): Result<TransactionUi> {
        return try {
            val response = expensesApiService.getTransactionById(id)
            when(response.code()){
                200 -> Result.success(response.body()!!.toTransactionUi())
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
    ): Result<List<TransactionUi>> {
        return try {
            val response = expensesApiService.getTransactionsByAccountIdWithDate(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            when(response.code()){
                200 -> Result.success(response.body()!!
                    .filter { !it.category.isIncome }
                    .map{ it.toTransactionUi() }
                )
                else -> Result.failure(Exception("${response.code()}"))
            }
        } catch(e : UnknownHostException){
            Result.failure(Exception("Нет подключения к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("$e"))
        }
    }
}