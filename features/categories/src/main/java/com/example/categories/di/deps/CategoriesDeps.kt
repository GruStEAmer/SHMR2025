package com.example.categories.di.deps

import com.example.data.repository.CategoryRepository


interface CategoriesDeps {

    val categoryRepository: CategoryRepository
}