package com.example.shmr.data.repository

import com.example.shmr.data.remote.api.AccountApiService
import com.example.shmr.domain.model.account.Account
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountHistoryResponse
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.model.account.AccountUpdateRequest
import com.example.shmr.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val accountApiService: AccountApiService
): AccountRepository {
    override suspend fun getAccounts(): Result<List<Account>> {
        return try{
            val response = accountApiService.getAccounts()

            when {
                response.isSuccessful -> Result.success(response.body()!!)
                response.code() == 401 -> Result.failure(Exception("Неавторизированный доступ"))
                else -> Result.failure(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun postAccount(accountUpdateRequest: AccountUpdateRequest): Result<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountById(id: Integer): Result<AccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun putAccountById(
        id: Integer,
        accountCreateRequest: AccountCreateRequest
    ): Result<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccountById(id: Integer): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountHistoryById(id: Integer): Result<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }
}
