package com.example.data.network.model.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: String,
    val previousState: AccountState?,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)
