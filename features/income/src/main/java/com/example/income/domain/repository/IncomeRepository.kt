package com.example.income.domain.repository

import com.example.income.data.model.TransactionResponse

interface IncomeRepository {

    suspend fun getTransactionByAccountIdWithDate(
        accountId: Int = 11,
        startDate:  String,
        endDate: String
    ) : Result<List<TransactionResponse>>

}