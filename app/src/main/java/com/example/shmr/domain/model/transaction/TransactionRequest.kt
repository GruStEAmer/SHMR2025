package com.example.shmr.domain.model.transaction

import java.time.LocalDateTime

data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?
)