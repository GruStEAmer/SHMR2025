package com.example.shmr.domain.model.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
