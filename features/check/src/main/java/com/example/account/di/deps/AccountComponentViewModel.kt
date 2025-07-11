package com.example.account.di.deps

import androidx.lifecycle.ViewModel
import com.example.account.di.component.AccountComponent
import com.example.account.di.component.DaggerAccountComponent

class AccountComponentViewModel: ViewModel() {
    val accountComponent: AccountComponent by lazy {
        DaggerAccountComponent.builder()
            .deps(AccountDepsProvider.accountDeps)
            .build()
    }
}