package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.AccountDao
import com.example.data.local.dao.CategoryDao
import com.example.data.local.dao.TransactionsDao
import com.example.data.local.entity.LocalAccount
import com.example.data.local.entity.LocalCategory
import com.example.data.local.entity.LocalTransaction

@Database(
    entities = [
        LocalAccount::class,
        LocalCategory::class,
        LocalTransaction::class
               ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionsDao
}