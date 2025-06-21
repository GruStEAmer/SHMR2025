package com.example.shmr.domain.model.transaction

import com.example.shmr.domain.model.account.AccountBrief
import com.example.shmr.domain.model.category.Category
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val id: Int,
    val account: AccountBrief,
    val category: Category,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)