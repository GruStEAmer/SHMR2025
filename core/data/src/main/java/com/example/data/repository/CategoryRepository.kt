package com.example.data.repository

import com.example.data.network.model.category.Category

interface CategoryRepository {
    suspend fun getCategories():Result<List<Category>>

    suspend fun getCategoriesByType(isIncome: Boolean) : Result<List<Category>>
}