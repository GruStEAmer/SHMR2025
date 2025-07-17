package com.example.expenses.ui.model

import com.example.network.model.transaction.TransactionResponse
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

data class TransactionResponseUi(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val accountName: String,
    val categoryName: String,
    val amount: String,
    val date: LocalDate,
    val time: LocalTime,
    val comment: String?,
)

fun TransactionResponse.toTransactionResponseUi(): TransactionResponseUi{
    val instant = Instant.parse(this.transactionDate)

    val zonedDateTime = instant.atZone(ZoneId.systemDefault())

    val date: LocalDate = zonedDateTime.toLocalDate()
    val timeAll: LocalTime = zonedDateTime.toLocalTime()

    val time = LocalTime.of(timeAll.hour, timeAll.minute)
//    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
//    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//
//    val dateStr = date.format(dateFormatter)
//    val timeStr = time.format(timeFormatter)

    return TransactionResponseUi(
        id = this.id,
        accountId = this.account.id,
        categoryId = this.category.id,
        accountName = this.account.name,
        categoryName = this.category.name,
        amount = this.amount,
        date = date,
        time = time,
        comment = this.comment
    )
}