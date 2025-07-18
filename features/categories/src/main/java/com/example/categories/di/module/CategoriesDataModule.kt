package com.example.categories.di.module

import com.example.categories.di.annotations.CategoriesScope
import com.example.categories.ui.screens.CategoryViewModel
import com.example.data.repository.CategoryRepository
import dagger.Module
import dagger.Provides

@Module
internal class CategoriesDataModule {
    @[Provides CategoriesScope]
    fun provideCategoryViewModel(categoryRepository: CategoryRepository) =
        CategoryViewModel(repository = categoryRepository)
}