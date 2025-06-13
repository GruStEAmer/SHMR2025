package com.example.shmr.domain.model.transaction

import com.example.shmr.domain.model.account.AccountBrief
import com.example.shmr.domain.model.category.Category
import java.time.LocalDateTime

data class TransactionResponse(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)