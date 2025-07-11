package com.example.shmr.di

import com.example.account.di.deps.AccountDeps
import com.example.categories.di.deps.CategoriesDeps
import com.example.network.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface AppComponent: CategoriesDeps, AccountDeps