package com.example.account.data.remote.apiService

import com.example.account.data.model.AccountCreateRequest
import com.example.account.data.model.AccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApiService {
    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): Response<AccountResponse>

    @PUT("accounts/{id}")
    suspend fun putAccountById(@Path("id") id: Int, @Body accountCreateRequest: AccountCreateRequest): Response<AccountResponse>
}