package com.example.expenses.di.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expenses.di.component.ExpensesComponent
import com.example.expenses.di.deps.ExpensesComponentViewModel

@Composable
fun rememberExpensesComponent(): ExpensesComponent {
    val componentViewModel: ExpensesComponentViewModel = viewModel()
    return remember { componentViewModel.expensesComponent }
}