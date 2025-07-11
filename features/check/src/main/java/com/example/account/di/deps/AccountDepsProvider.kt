package com.example.account.di.deps

interface AccountDepsProvider {

    val accountDeps: AccountDeps

    companion object : AccountDepsProvider by AccountDepsStore
}