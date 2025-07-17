package com.example.account.domain.repository

import com.example.shmr.domain.model.account.AccountCreateRequest
import com.example.shmr.domain.model.account.AccountResponse

interface AccountRepository {
    suspend fun getAccountById(id: Int = 11): Result<AccountResponse>

    suspend fun putAccountById(id: Int = 11, accountCreateRequest: AccountCreateRequest) : Result<AccountResponse>
}

