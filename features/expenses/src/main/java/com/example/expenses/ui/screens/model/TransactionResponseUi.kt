package com.example.expenses.ui.screens.model

import com.example.expenses.data.model.TransactionResponse
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TransactionResponseUi(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val accountName: String,
    val categoryName: String,
    val amount: String,
    val date: String,
    val time: String,
    val comment: String?,
)

fun TransactionResponse.toTransactionResponseUi(): TransactionResponseUi{
    val instant = Instant.parse(this.transactionDate)

    val zonedDateTime = instant.atZone(ZoneId.systemDefault())

    val date: LocalDate = zonedDateTime.toLocalDate()
    val time: LocalTime = zonedDateTime.toLocalTime()

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    val dateStr = date.format(dateFormatter)
    val timeStr = time.format(timeFormatter)

    return TransactionResponseUi(
        id = this.id,
        accountId = this.account.id,
        categoryId = this.category.id,
        accountName = this.account.name,
        categoryName = this.category.name,
        amount = this.amount,
        date = dateStr,
        time = timeStr,
        comment = this.comment
    )
}