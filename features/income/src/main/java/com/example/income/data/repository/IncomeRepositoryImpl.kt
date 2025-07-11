package com.example.income.data.repository

import com.example.income.data.model.TransactionResponse
import com.example.income.data.remote.apiService.IncomeApiService
import com.example.income.domain.repository.IncomeRepository
import java.net.UnknownHostException
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val incomeApiService: IncomeApiService
): IncomeRepository {
    override suspend fun getTransactionByAccountIdWithDate(
        accountId: Int,
        startDate: String,
        endDate: String
    ): Result<List<TransactionResponse>> {
        return try {
            val response = incomeApiService.getTransactionsByAccountIdWithDate(
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