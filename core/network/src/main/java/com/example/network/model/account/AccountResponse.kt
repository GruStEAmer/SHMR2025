package com.example.network.model.account

import com.example.network.model.statItem.StatItem
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val expenseStats: List<StatItem>,
    val createdAt: String,
    val updatedAt: String
)
