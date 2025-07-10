package com.example.categories.domain.repository

import com.example.categories.data.model.Category

interface CategoryRepository {
    suspend fun getCategories():Result<List<Category>>

    suspend fun getCategoriesByType(isIncome: Boolean) : Result<List<Category>>
}
