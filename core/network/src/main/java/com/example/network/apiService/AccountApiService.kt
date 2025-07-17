package com.example.network.apiService

import com.example.shmr.domain.model.account.Account
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountHistoryResponse
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.model.account.AccountUpdateRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApiService {
    @GET("accounts")
    suspend fun getAccounts(): Response<List<Account>>

    @POST("accounts")
    suspend fun postAccount(@Body accountUpdateRequest: AccountUpdateRequest): Response<Account>

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): Response<AccountResponse>

    @PUT("accounts/{id}")
    suspend fun putAccountById(@Path("id") id: Int, @Body accountCreateRequest: AccountCreateRequest): Response<AccountResponse>

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(@Path("id") id: Int): Response<Unit>

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(@Path("id") id: Int): Response<AccountHistoryResponse>
}