package com.example.data.repository

import com.example.data.local.entity.LocalAccount
import com.example.data.network.model.account.AccountUpdateRequest

interface AccountRepository {
    suspend fun getAccountById(id: Int): Result<LocalAccount>

    suspend fun putAccountById(id: Int, accountUpdateRequest: AccountUpdateRequest): Result<LocalAccount>

    suspend fun refreshAccountById(id: Int): Result<Unit>

    suspend fun getUnsyncedAccounts(): Result<List<LocalAccount>>

    suspend fun syncAccountToServer(account: LocalAccount): Result<Unit>
}