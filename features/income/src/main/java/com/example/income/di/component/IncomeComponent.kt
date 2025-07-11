package com.example.income.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.income.di.annotations.IncomeScope
import com.example.income.di.deps.IncomeDeps
import com.example.income.di.module.IncomeDataModule
import com.example.income.di.module.ViewModelFactoryModule
import com.example.income.di.module.ViewModelModule
import dagger.Component

@[IncomeScope Component(
    dependencies = [IncomeDeps::class],
    modules = [IncomeDataModule::class, ViewModelModule::class, ViewModelFactoryModule::class]
)]
interface IncomeComponent {

    fun viewModelFactory(): ViewModelProvider.Factory
    @Component.Builder
    interface Builder{
        fun deps(incomeDeps: IncomeDeps): Builder
        fun build(): IncomeComponent
    }
}