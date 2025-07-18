package com.example.data.di

import com.example.data.local.dao.AccountDao
import com.example.data.network.apiService.AccountApiService
import com.example.data.network.apiService.CategoryApiService
import com.example.data.network.apiService.TransactionApiService
import com.example.data.repository.AccountRepository
import com.example.data.repository.CategoryRepository
import com.example.data.repository.TransactionRepository
import com.example.data.repositoryImpl.AccountRepositoryImpl
import com.example.data.repositoryImpl.CategoryRepositoryImpl
import com.example.data.repositoryImpl.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @[Provides Singleton]
    fun provideExpensesRepository(transactionApiService: TransactionApiService): TransactionRepository =
        TransactionRepositoryImpl(transactionApiService = transactionApiService)

    @[Provides Singleton]
    fun provideCategoryRepository(categoryApiService: CategoryApiService): CategoryRepository =
        CategoryRepositoryImpl(categoryApiService = categoryApiService)

    @[Provides Singleton]
    fun provideAccountRepository(
        accountApiService: AccountApiService,
        accountDao: AccountDao
    ): AccountRepository =
        AccountRepositoryImpl(
            accountApiService = accountApiService,
            accountDao = accountDao
        )
}