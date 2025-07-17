package com.example.income.data.remote.apiService

import com.example.income.data.model.Transaction
import com.example.income.data.model.TransactionRequest
import com.example.income.data.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface IncomeApiService {

    @POST("transactions")
    suspend fun postTransaction(@Body transactionRequest: TransactionRequest): Response<Transaction>

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int) : Response<TransactionResponse>

    @PUT("transactions/{id}")
    suspend fun putTransactionById(@Path("id") id: Int, @Body transactionRequest: TransactionRequest): Response<TransactionResponse>

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactionsByAccountIdWithDate(
        @Path("accountId") accountId:Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<TransactionResponse>>

}