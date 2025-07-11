package com.example.expenses.domain.repository

import com.example.expenses.data.model.TransactionResponse

interface ExpensesRepository {

    suspend fun getTransactionByAccountIdWithDate(
        accountId: Int = 11,
        startDate:  String,
        endDate: String
    ) : Result<List<TransactionResponse>>

}