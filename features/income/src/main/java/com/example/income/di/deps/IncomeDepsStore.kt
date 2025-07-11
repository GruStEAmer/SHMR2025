package com.example.income.di.deps

import kotlin.properties.Delegates.notNull

object IncomeDepsStore: IncomeDepsProvider {
    override var incomeDeps: IncomeDeps by notNull()
}