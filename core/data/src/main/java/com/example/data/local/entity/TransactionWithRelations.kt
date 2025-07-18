package com.example.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithRelations(
    @Embedded val transaction: LocalTransaction,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
        entity = LocalCategory::class
    )
    val category: LocalCategory,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "id",
        entity = LocalAccount::class
    )
    val account: LocalAccount
)