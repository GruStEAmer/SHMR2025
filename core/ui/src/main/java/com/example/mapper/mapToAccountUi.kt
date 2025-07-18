package com.example.mapper

import com.example.data.local.entity.LocalAccount
import com.example.data.network.model.account.AccountResponse
import com.example.model.AccountUi

fun AccountResponse.toAccountUi() =
    AccountUi(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = when (currency) {
            "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            else -> ""
        }
    )
 fun LocalAccount.toAccountUi() =
     AccountUi(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
    )

fun AccountResponse.toLocalAccount() =
    LocalAccount(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        isSynced = true,
        lastModified = System.currentTimeMillis()
    )

