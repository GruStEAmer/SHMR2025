package com.example.income.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)