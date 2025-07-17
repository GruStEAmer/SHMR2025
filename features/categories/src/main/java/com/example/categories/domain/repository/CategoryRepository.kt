package com.example.categories.domain.repository

import com.example.categories.ui.model.CategoryUi

interface CategoryRepository {
    suspend fun getCategories():Result<List<CategoryUi>>

    suspend fun getCategoriesByType(isIncome: Boolean) : Result<List<CategoryUi>>
}
