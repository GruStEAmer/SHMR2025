package com.example.expenses.data.remote.apiService

import com.example.expenses.data.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExpensesApiService {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountIdWithDate(
        @Path("accountId") accountId:Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<TransactionResponse>>

}