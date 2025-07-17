package com.example.network.model.transaction

import com.example.network.model.account.AccountBrief
import com.example.network.model.category.Category
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