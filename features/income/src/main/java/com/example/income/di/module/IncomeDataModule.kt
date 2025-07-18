package com.example.income.di.module

import com.example.data.repository.TransactionRepository
import com.example.income.di.annotations.IncomeScope
import com.example.income.ui.screens.IncomeViewModel
import dagger.Module
import dagger.Provides

@Module
internal class IncomeDataModule {

    @[Provides IncomeScope]
    fun provideIncomeViewModel(transactionRepository: TransactionRepository) =
        IncomeViewModel(repository = transactionRepository)
}