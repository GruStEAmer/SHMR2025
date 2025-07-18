package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = LocalAccount::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocalCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class LocalTransaction(
    @PrimaryKey val id: Int,
    val amount: Double,
    val comment: String?,

    val transactionDate: String,

    val accountId: Int,

    val categoryId: Int,

    var isSynced: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),

    var updatedAt: Long = System.currentTimeMillis()
)
