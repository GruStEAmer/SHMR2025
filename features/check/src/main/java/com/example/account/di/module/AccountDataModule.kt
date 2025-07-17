package com.example.account.di.module

import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.di.annotations.AccountScope
import com.example.account.domain.repository.AccountRepository
import com.example.account.ui.screens.AccountViewModel
import com.example.network.apiService.AccountApiService
import dagger.Module
import dagger.Provides

@Module
internal class AccountDataModule {

    @[Provides AccountScope]
    fun provideAccountRepository(accountApiService: AccountApiService): AccountRepository =
        AccountRepositoryImpl(accountApiService = accountApiService)

    @[Provides AccountScope]
    fun provideAccountViewModel(accountRepository: AccountRepository) =
        AccountViewModel(repository = accountRepository)
}