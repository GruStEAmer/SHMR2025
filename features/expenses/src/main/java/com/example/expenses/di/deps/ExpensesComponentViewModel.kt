package com.example.expenses.di.deps

import androidx.lifecycle.ViewModel
import com.example.expenses.di.component.DaggerExpensesComponent
import com.example.expenses.di.component.ExpensesComponent

class ExpensesComponentViewModel: ViewModel() {
    val expensesComponent: ExpensesComponent by lazy {
        DaggerExpensesComponent.builder()
            .deps(ExpensesDepsProvider.expensesDeps)
            .build()
    }
}