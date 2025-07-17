package com.example.income.di.deps

import com.example.network.apiService.TransactionApiService

interface IncomeDeps {

    val transactionApiService: TransactionApiService
}