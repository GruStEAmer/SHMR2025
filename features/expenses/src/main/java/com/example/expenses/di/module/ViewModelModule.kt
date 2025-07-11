package com.example.expenses.di.module

import androidx.lifecycle.ViewModel
import com.example.expenses.di.annotations.ViewModelKey
import com.example.expenses.ui.screens.ExpensesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    abstract fun bindExpensesViewModel(viewModel: ExpensesViewModel): ViewModel
}