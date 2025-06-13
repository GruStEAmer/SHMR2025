package com.example.shmr.domain.model.account

import java.time.LocalDateTime

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: String,
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimestamp: LocalDateTime,
    val createdAt: LocalDateTime
)
