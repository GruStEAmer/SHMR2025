package com.example.account.di.deps

import com.example.data.repository.AccountRepository

interface AccountDeps {

    val accountRepository: AccountRepository
}