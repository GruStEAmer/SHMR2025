package com.example.income.ui.model

import java.time.LocalDate
import java.time.LocalTime

data class TransactionUi(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val categoryEmoji: String,
    val accountName: String,
    val categoryName: String,
    val accountCurrency: String,
    val amount: String,
    val date: LocalDate,
    val time: LocalTime,
    val dateTime: String,
    val comment: String?,
)