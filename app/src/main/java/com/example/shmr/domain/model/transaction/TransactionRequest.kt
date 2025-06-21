package com.example.shmr.domain.model.transaction

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TransactionRequest(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)