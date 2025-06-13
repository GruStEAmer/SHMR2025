package com.example.shmr.domain.model.account

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
