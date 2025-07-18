package com.example.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: Int,
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
