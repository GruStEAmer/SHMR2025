package com.example.expenses.di.deps

import kotlin.properties.Delegates.notNull

object ExpensesDepsStore: ExpensesDepsProvider {
    override var expensesDeps: ExpensesDeps by notNull()
}