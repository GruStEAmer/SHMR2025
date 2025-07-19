package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.LocalCategory

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<LocalCategory>

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome")
    suspend fun getCategoriesByType(isIncome: Boolean): List<LocalCategory>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): LocalCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceCategories(categories: List<LocalCategory>)

    @Transaction
    suspend fun replaceAllCategories(categories: List<LocalCategory>) {
        deleteAllCategories()
        insertOrReplaceCategories(categories)
    }

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}