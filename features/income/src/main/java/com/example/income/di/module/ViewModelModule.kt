package com.example.income.di.module

import androidx.lifecycle.ViewModel
import com.example.income.di.annotations.ViewModelKey
import com.example.income.ui.screens.IncomeDetailByIdViewModel
import com.example.income.ui.screens.IncomeHistoryViewModel
import com.example.income.ui.screens.IncomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(IncomeViewModel::class)
    abstract fun bindIncomeViewModel(viewModel: IncomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeHistoryViewModel::class)
    abstract fun bindIncomeHistoryViewModel(viewModel: IncomeHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeDetailByIdViewModel::class)
    abstract fun bindIncomeDetailByIdViewModel(viewModel: IncomeDetailByIdViewModel): ViewModel
}