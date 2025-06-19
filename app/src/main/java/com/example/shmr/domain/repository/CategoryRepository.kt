package com.example.shmr.domain.repository

import com.example.shmr.domain.model.category.Category

interface CategoryRepository {
    suspend fun getCategories():Result<List<Category>>

    suspend fun getCategoriesByType(isIncome: Boolean) : Result<List<Category>>
}
