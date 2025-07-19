package com.example.data.repository

import com.example.data.local.entity.LocalCategory

interface CategoryRepository {
    suspend fun getCategories(): Result<List<LocalCategory>>

    suspend fun getCategoriesByType(isIncome: Boolean): Result<List<LocalCategory>>

    suspend fun refreshAllCategoriesFromNetwork(): Result<Unit>

}