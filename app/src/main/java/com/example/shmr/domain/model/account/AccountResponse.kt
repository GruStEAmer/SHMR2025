package com.example.shmr.domain.model.account

import com.example.shmr.domain.model.stats.StatItem
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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
