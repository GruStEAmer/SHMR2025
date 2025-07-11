package com.example.account.di.deps

import kotlin.properties.Delegates.notNull

object AccountDepsStore: AccountDepsProvider {

    override var accountDeps: AccountDeps by notNull()
}