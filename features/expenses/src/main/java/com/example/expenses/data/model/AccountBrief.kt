package com.example.expenses.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
