package com.example.expenses.di.deps

import com.example.network.apiService.TransactionApiService
import retrofit2.Retrofit

interface ExpensesDeps {

    val transactionApiService: TransactionApiService
}