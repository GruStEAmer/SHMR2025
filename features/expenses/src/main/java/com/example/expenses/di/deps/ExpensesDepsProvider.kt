package com.example.expenses.di.deps

interface ExpensesDepsProvider {

    val expensesDeps: ExpensesDeps

    companion object : ExpensesDepsProvider by ExpensesDepsStore
}