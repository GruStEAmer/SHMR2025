package com.example.account.domain.repository

import com.example.account.data.model.AccountCreateRequest
import com.example.account.data.model.AccountResponse

interface AccountRepository {
    suspend fun getAccountById(id: Int = 11): Result<AccountResponse>

    suspend fun putAccountById(id: Int = 11, accountCreateRequest: AccountCreateRequest) : Result<AccountResponse>
}

