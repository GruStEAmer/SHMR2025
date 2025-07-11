package com.example.account.di.module

import com.example.account.data.remote.apiService.AccountApiService
import com.example.account.data.repository.AccountRepositoryImpl
import com.example.account.di.annotations.AccountScope
import com.example.account.domain.repository.AccountRepository
import com.example.account.ui.screens.AccountViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
internal class AccountDataModule {

    @[Provides AccountScope]
    fun provideAccountApiService(retrofit: Retrofit): AccountApiService {
        return retrofit.create<AccountApiService>()
    }

    @[Provides AccountScope]
    fun provideAccountRepository(accountApiService: AccountApiService): AccountRepository =
        AccountRepositoryImpl(accountApiService = accountApiService)

    @[Provides AccountScope]
    fun provideAccountViewModel(accountRepository: AccountRepository) =
        AccountViewModel(repository = accountRepository)
}