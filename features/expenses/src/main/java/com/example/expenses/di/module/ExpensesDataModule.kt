package com.example.expenses.di.module

import com.example.expenses.data.repository.ExpensesRepositoryImpl
import com.example.expenses.di.annotations.ExpensesScope
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.ui.screens.ExpensesViewModel
import com.example.network.apiService.TransactionApiService
import dagger.Module
import dagger.Provides

@Module
internal class ExpensesDataModule {

    @[Provides ExpensesScope]
    fun provideExpensesRepository(expensesApiService: TransactionApiService): ExpensesRepository =
        ExpensesRepositoryImpl(expensesApiService = expensesApiService)

    @[Provides ExpensesScope]
    fun provideExpensesViewModel(expensesRepository: ExpensesRepository) =
        ExpensesViewModel(repository = expensesRepository)
}