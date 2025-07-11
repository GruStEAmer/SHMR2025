package com.example.income.di.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.income.di.component.IncomeComponent
import com.example.income.di.deps.IncomeComponentViewModel

@Composable
fun rememberIncomeComponent(): IncomeComponent {
    val componentViewModel: IncomeComponentViewModel = viewModel()
    return remember { componentViewModel.incomeComponent }
}