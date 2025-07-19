package com.example.expenses.di.module

import androidx.lifecycle.ViewModel
import com.example.expenses.di.annotations.ViewModelKey
import com.example.expenses.ui.screens.ExpensesAnalysisViewModel
import com.example.expenses.ui.screens.ExpensesDetailByIdViewModel
import com.example.expenses.ui.screens.ExpensesHistoryViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesHistoryViewModel::class)
    abstract fun bindExpensesHistoryViewModel(viewModel: ExpensesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesDetailByIdViewModel::class)
    abstract fun bindExpensesDetailByIdViewModel(viewModel: ExpensesDetailByIdViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesAnalysisViewModel::class)
    abstract fun bindExpensesAnalysisViewModel(viewModel: ExpensesAnalysisViewModel): ViewModel
}