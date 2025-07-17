package com.example.network.model.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
