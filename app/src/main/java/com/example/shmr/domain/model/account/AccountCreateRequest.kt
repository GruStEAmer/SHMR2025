package com.example.shmr.domain.model.account

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
