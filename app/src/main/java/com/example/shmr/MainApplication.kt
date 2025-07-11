package com.example.shmr

import android.app.Application
import com.example.account.di.deps.AccountDepsStore
import com.example.categories.di.deps.CategoriesDepsStore
import com.example.expenses.di.deps.ExpensesDepsStore
import com.example.income.di.deps.IncomeDepsStore
import com.example.shmr.di.AppComponent
import com.example.shmr.di.AppContainer
import com.example.shmr.di.DaggerAppComponent
import com.example.shmr.di.DefaultAppContainer

class MainApplication: Application() {
    lateinit var container: AppContainer

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()

        CategoriesDepsStore.categoriesDeps = appComponent
        AccountDepsStore.accountDeps = appComponent
        ExpensesDepsStore.expensesDeps = appComponent
        IncomeDepsStore.incomeDeps = appComponent

        container = DefaultAppContainer()
    }
}
