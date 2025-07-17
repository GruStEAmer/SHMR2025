package com.example.account.ui.mapper

import com.example.account.ui.model.AccountUi
import com.example.network.model.account.AccountResponse

fun AccountResponse.toAccountUi() =
    AccountUi(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = when(currency) {
            "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            else -> ""
        }
    )