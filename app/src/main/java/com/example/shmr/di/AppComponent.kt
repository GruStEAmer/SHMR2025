package com.example.shmr.di

import android.content.Context
import com.example.account.di.deps.AccountDeps
import com.example.categories.di.deps.CategoriesDeps
import com.example.data.di.NetworkModule
import com.example.expenses.di.deps.ExpensesDeps
import com.example.income.di.deps.IncomeDeps
import com.example.data.di.AppDatabaseModule
import com.example.data.di.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AppDatabaseModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent: CategoriesDeps, AccountDeps, ExpensesDeps, IncomeDeps {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}