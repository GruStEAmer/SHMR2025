package com.example.account.domain.repository

import com.example.account.ui.model.AccountUi
import com.example.network.model.account.AccountUpdateRequest

interface AccountRepository {
    suspend fun getAccountById(id: Int): Result<AccountUi>

    suspend fun putAccountById(id: Int, accountUpdateRequest: AccountUpdateRequest): Result<AccountUi>

    suspend fun refreshAccountById(id: Int): Result<Unit>

}

