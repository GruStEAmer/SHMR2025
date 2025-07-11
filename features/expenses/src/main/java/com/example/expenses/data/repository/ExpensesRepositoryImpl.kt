package com.example.expenses.data.repository

import com.example.expenses.data.model.TransactionResponse
import com.example.expenses.data.remote.apiService.ExpensesApiService
import com.example.expenses.domain.repository.ExpensesRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApiService: ExpensesApiService
): ExpensesRepository {
    override suspend fun getTransactionByAccountIdWithDate(
        accountId: Int,
        startDate: String,
        endDate: String
    ): Result<List<TransactionResponse>> {
        return try {
            val response = expensesApiService.getTransactionsByAccountIdWithDate(
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