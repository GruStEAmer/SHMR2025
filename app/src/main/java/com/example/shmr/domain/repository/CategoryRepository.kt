package com.example.shmr.domain.repository

import com.example.shmr.domain.model.category.Category

interface CategoryRepository {
    suspend fun getCategories():List<Category>

    suspend fun getCategoriesByType(isIncome: Boolean) : List<Category>
}
