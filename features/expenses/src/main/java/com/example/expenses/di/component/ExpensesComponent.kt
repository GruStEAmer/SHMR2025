package com.example.expenses.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.expenses.di.annotations.ExpensesScope
import com.example.expenses.di.deps.ExpensesDeps
import com.example.expenses.di.module.ExpensesDataModule
import com.example.expenses.di.module.ViewModelFactoryModule
import com.example.expenses.di.module.ViewModelModule
import dagger.Component

@[ExpensesScope Component(
    dependencies = [ExpensesDeps::class],
    modules = [ExpensesDataModule::class, ViewModelModule::class, ViewModelFactoryModule::class]
)]
interface ExpensesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {
        fun deps(expensesDeps: ExpensesDeps): Builder
        fun build(): ExpensesComponent
    }
}