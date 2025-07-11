package com.example.account.data.repository

import com.example.account.data.model.AccountCreateRequest
import com.example.account.data.model.AccountResponse
import com.example.account.data.remote.apiService.AccountApiService
import com.example.account.domain.repository.AccountRepository
import java.net.UnknownHostException
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApiService: AccountApiService
): AccountRepository {

    override suspend fun getAccountById(id: Int): Result<AccountResponse> {
        return try {
            val response = accountApiService.getAccountById(id)

            when(response.code()){
                200 -> Result.success(response.body()!!)
                else -> Result.failure(Exception("${response.code()}"))
            }
        } catch (e: UnknownHostException){
            Result.failure(Exception("Нет подключение к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("${e.message}"))
        }
    }

    override suspend fun putAccountById(
        id: Int,
        accountCreateRequest: AccountCreateRequest
    ): Result<AccountResponse> {
        return try {
            val response = accountApiService.putAccountById(id, accountCreateRequest)

            when(response.code()){
                200 -> Result.success(response.body()!!)
                else -> Result.failure(Exception("${response.code()}"))
            }
        } catch (e: UnknownHostException){
            Result.failure(Exception("Нет подключение к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("${e.message}"))
        }
    }
}
