package com.example.account.ui.mapper

import com.example.account.ui.model.AccountUi
import com.example.local.entity.LocalAccount
import com.example.network.model.account.AccountCreateRequest
import com.example.network.model.account.AccountResponse
import com.example.network.model.account.AccountUpdateRequest

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

