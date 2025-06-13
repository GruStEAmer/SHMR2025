package com.example.shmr.domain.model.account

import com.example.shmr.domain.model.stats.StatItem
import java.time.LocalDateTime

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatItem>,
    val expenseStats: List<StatItem>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
