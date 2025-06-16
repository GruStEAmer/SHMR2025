package com.example.shmr.ui.model

data class TransactionUi(
    val id: Int,
    val name: String,
    val emoji: String?,
    val categoryId: Int,
    val amount: String,
    val comment: String?
)
