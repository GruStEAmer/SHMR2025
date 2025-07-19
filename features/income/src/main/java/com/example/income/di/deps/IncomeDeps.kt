package com.example.income.di.deps

import com.example.data.repository.TransactionRepository

interface IncomeDeps {

    val transactionRepository: TransactionRepository
}