package com.example.shmr.domain.repository

import com.example.shmr.domain.model.account.Account
import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountHistoryResponse
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.domain.model.account.AccountUpdateRequest


interface AccountRepository {
    suspend fun getAccounts() : Result<List<Account>>

    suspend fun postAccount(accountUpdateRequest: AccountUpdateRequest): Result<Account>

    suspend fun getAccountById(id: Int): Result<AccountResponse>

    suspend fun putAccountById(id: Int, accountCreateRequest: AccountCreateRequest) : Result<AccountResponse>

    suspend fun deleteAccountById(id: Int): Result<Unit>

    suspend fun getAccountHistoryById(id: Int): Result<AccountHistoryResponse>
}

