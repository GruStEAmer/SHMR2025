package com.example.shmr.domain.model.account

import java.time.LocalDateTime

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
