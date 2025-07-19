package com.example.expenses.di.deps

import com.example.data.repository.TransactionRepository

interface ExpensesDeps {

    val transactionRepository: TransactionRepository
}