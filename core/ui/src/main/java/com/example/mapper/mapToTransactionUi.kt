package com.example.mapper

import com.example.data.network.model.transaction.TransactionResponse
import com.example.model.TransactionUi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

fun TransactionResponse.toTransactionUi(): TransactionUi {
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

    return TransactionUi(
        id = this.id,
        accountId = this.account.id,
        categoryId = this.category.id,
        accountName = this.account.name,
        categoryName = this.category.name,
        categoryEmoji = this.category.emoji,
        accountCurrency = this.account.currency,
        isIncome = this.category.isIncome,
        amount = this.amount,
        date = date,
        time = time,
        dateTime = this.transactionDate,
        comment = this.comment
    )
}