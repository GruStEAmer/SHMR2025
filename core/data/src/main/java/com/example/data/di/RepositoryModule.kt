package com.example.data.di

import com.example.data.local.dao.AccountDao
import com.example.data.local.dao.CategoryDao
import com.example.data.network.apiService.AccountApiService
import com.example.data.network.apiService.CategoryApiService
import com.example.data.network.apiService.TransactionApiService
import com.example.data.repository.AccountRepository
import com.example.data.repository.CategoryRepository
import com.example.data.repository.TransactionRepository
import com.example.data.repositoryImpl.AccountRepositoryImpl
import com.example.data.repositoryImpl.CategoryRepositoryImpl
import com.example.data.repositoryImpl.TransactionRepositoryImpl
import com.example.data.local.dao.TransactionsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @[Provides Singleton]
    fun provideExpensesRepository(
        transactionApiService: TransactionApiService,
        transactionDao: TransactionsDao,
        accountDao: AccountDao,
        categoryDao: CategoryDao
    ): TransactionRepository =
        TransactionRepositoryImpl(
            transactionApiService = transactionApiService,
            transactionsDao = transactionDao,
            accountDao = accountDao,
            categoryDao = categoryDao
        )

    @[Provides Singleton]
    fun provideCategoryRepository(
        categoryApiService: CategoryApiService,
        categoryDao: CategoryDao
    ): CategoryRepository =
        CategoryRepositoryImpl(
            categoryApiService = categoryApiService,
            categoryDao = categoryDao
        )

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