package com.example.shmr.di

import android.content.Context
import com.example.account.di.deps.AccountDeps
import com.example.categories.di.deps.CategoriesDeps
import com.example.expenses.di.deps.ExpensesDeps
import com.example.income.di.deps.IncomeDeps
import com.example.local.di.AppDatabaseModule
import com.example.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AppDatabaseModule::class
    ]
)
interface AppComponent: CategoriesDeps, AccountDeps, ExpensesDeps, IncomeDeps {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}