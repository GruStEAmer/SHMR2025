package com.example.account.data.repository

import com.example.account.domain.repository.AccountRepository
import com.example.network.apiService.AccountApiService
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountResponse
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
