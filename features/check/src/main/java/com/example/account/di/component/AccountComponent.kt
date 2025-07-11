package com.example.account.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.account.di.annotations.AccountScope
import com.example.account.di.deps.AccountDeps
import com.example.account.di.module.AccountDataModule
import com.example.account.di.module.ViewModelFactoryModule
import com.example.account.di.module.ViewModelModule
import dagger.Component

@[AccountScope Component(
    dependencies = [ AccountDeps::class ],
    modules = [ AccountDataModule::class, ViewModelModule::class, ViewModelFactoryModule::class ]
)]
interface AccountComponent{

    fun viewModelFactory(): ViewModelProvider.Factory
    @Component.Builder
    interface Builder{
        fun deps(accountDeps: AccountDeps): Builder

        fun build(): AccountComponent
    }
}