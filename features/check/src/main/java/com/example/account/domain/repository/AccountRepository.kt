package com.example.account.domain.repository

import com.example.account.ui.model.AccountUi
import com.example.network.model.account.AccountCreateRequest

interface AccountRepository {
    suspend fun getAccountById(id: Int = 11): Result<AccountUi>

    suspend fun putAccountById(id: Int = 11, accountCreateRequest: AccountCreateRequest) : Result<AccountUi>
}

