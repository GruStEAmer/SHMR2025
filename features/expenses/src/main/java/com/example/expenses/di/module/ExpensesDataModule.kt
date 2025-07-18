package com.example.expenses.di.module

import com.example.data.repository.TransactionRepository
import com.example.expenses.di.annotations.ExpensesScope
import com.example.expenses.ui.screens.ExpensesViewModel
import dagger.Module
import dagger.Provides

@Module
internal class ExpensesDataModule {

    @[Provides ExpensesScope]
    fun provideExpensesViewModel(transactionRepository: TransactionRepository) =
        ExpensesViewModel(repository = transactionRepository)
}