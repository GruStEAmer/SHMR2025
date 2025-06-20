package com.example.shmr.data.repository

import com.example.shmr.data.remote.api.AccountApiService
import com.example.shmr.domain.model.account.Account
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountHistoryResponse
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.model.account.AccountUpdateRequest
import com.example.shmr.domain.repository.AccountRepository
import java.net.UnknownHostException

class AccountRepositoryImpl(
    private val accountApiService: AccountApiService
): AccountRepository {

    override suspend fun getAccounts(): Result<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun postAccount(accountUpdateRequest: AccountUpdateRequest): Result<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountById(id: Int): Result<AccountResponse> {
        return try {
            val response = accountApiService.getAccountById(id)

            when(response.code()){
                200 -> Result.success(response.body()!!)
                else -> Result.failure(Exception("Error ${response.code()}: ${response.body()}"))
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
    ): Result<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccountById(id: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountHistoryById(id: Int): Result<AccountHistoryResponse> {
        TODO("Not yet implemented")
    }
}
