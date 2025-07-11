package com.example.expenses.di.module

import com.example.expenses.data.remote.apiService.ExpensesApiService
import com.example.expenses.data.repository.ExpensesRepositoryImpl
import com.example.expenses.di.annotations.ExpensesScope
import com.example.expenses.domain.repository.ExpensesRepository
import com.example.expenses.ui.screens.ExpensesViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
internal class ExpensesDataModule {

    @[Provides ExpensesScope]
    fun provideExpensesApiService(retrofit: Retrofit): ExpensesApiService {
        return retrofit.create<ExpensesApiService>()
    }

    @[Provides ExpensesScope]
    fun provideExpensesRepository(expensesApiService: ExpensesApiService): ExpensesRepository =
        ExpensesRepositoryImpl(expensesApiService = expensesApiService)

    @[Provides ExpensesScope]
    fun provideExpensesViewModel(expensesRepository: ExpensesRepository) =
        ExpensesViewModel(repository = expensesRepository)
}