package com.example.account.di.deps

import com.example.data.repository.AccountRepository
import com.example.data.repository.TransactionRepository

interface AccountDeps {

    val accountRepository: AccountRepository

    val transactionRepository: TransactionRepository
}