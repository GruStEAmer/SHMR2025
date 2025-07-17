package com.example.income.di.module

import com.example.income.data.repository.IncomeRepositoryImpl
import com.example.income.di.annotations.IncomeScope
import com.example.income.domain.repository.IncomeRepository
import com.example.income.ui.screens.IncomeViewModel
import com.example.network.apiService.TransactionApiService
import dagger.Module
import dagger.Provides

@Module
internal class IncomeDataModule {

    @[Provides IncomeScope]
    fun provideIncomeRepository(incomeApiService: TransactionApiService): IncomeRepository =
        IncomeRepositoryImpl(incomeApiService = incomeApiService)

    @[Provides IncomeScope]
    fun provideIncomeViewModel(incomeRepository: IncomeRepository) =
        IncomeViewModel(repository = incomeRepository)
}