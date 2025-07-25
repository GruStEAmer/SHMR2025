package com.example.account.di.module

import com.example.account.di.annotations.AccountScope
import com.example.account.ui.screens.AccountViewModel
import com.example.data.repository.AccountRepository
import com.example.data.repository.TransactionRepository
import dagger.Module
import dagger.Provides

@Module
internal class AccountDataModule {



    @[Provides AccountScope]
    fun provideAccountViewModel(
        accountRepository: AccountRepository,
        transactionRepository: TransactionRepository
    ) =
        AccountViewModel(
            repository = accountRepository,
            transactionRep = transactionRepository
        )
}