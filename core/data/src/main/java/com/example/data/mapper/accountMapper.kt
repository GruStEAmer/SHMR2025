package com.example.data.mapper

import com.example.data.local.entity.LocalAccount
import com.example.data.network.model.account.AccountResponse

fun AccountResponse.toLocalAccount() =
    LocalAccount(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency,
        isSynced = true
    )