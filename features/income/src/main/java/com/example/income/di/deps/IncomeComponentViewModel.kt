package com.example.income.di.deps

import androidx.lifecycle.ViewModel
import com.example.income.di.component.DaggerIncomeComponent
import com.example.income.di.component.IncomeComponent

class IncomeComponentViewModel: ViewModel() {
    val incomeComponent: IncomeComponent by lazy {
        DaggerIncomeComponent.builder()
            .deps(IncomeDepsProvider.incomeDeps)
            .build()
    }
}