package com.example.categories.di.module

import com.example.categories.data.repository.CategoryRepositoryImpl
import com.example.categories.di.annotations.CategoriesScope
import com.example.categories.domain.repository.CategoryRepository
import com.example.categories.ui.screens.CategoryViewModel
import com.example.network.apiService.CategoryApiService
import dagger.Module
import dagger.Provides

@Module
internal class CategoriesDataModule {
    @[Provides CategoriesScope]
    fun provideCategoryRepository(categoryApiService: CategoryApiService): CategoryRepository =
        CategoryRepositoryImpl(categoryApiService = categoryApiService)

    @[Provides CategoriesScope]
    fun provideCategoryViewModel(categoryRepository: CategoryRepository) =
        CategoryViewModel(repository = categoryRepository)
}