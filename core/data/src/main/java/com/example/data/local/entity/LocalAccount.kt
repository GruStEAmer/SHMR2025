package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class LocalAccount(
    @PrimaryKey val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    var lastModified: Long = System.currentTimeMillis(),
    var isSynced: Boolean = false
)