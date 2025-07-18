package com.example.account.di.deps

import com.example.local.dao.AccountDao
import com.example.network.apiService.AccountApiService

interface AccountDeps {

    val accountApiService: AccountApiService

    val accountDao: AccountDao
}