package com.example.categories.di.module

import com.example.categories.data.remote.apiService.CategoryApiService
import com.example.categories.data.repository.CategoryRepositoryImpl
import com.example.categories.di.annotations.CategoriesScope
import com.example.categories.domain.repository.CategoryRepository
import com.example.categories.ui.screens.CategoryViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
internal class CategoriesDataModule {

    @[Provides CategoriesScope]
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
        return retrofit.create<CategoryApiService>()
    }

    @[Provides CategoriesScope]
    fun provideCategoryRepository(categoryApiService: CategoryApiService): CategoryRepository =
        CategoryRepositoryImpl(categoryApiService = categoryApiService)

    @[Provides CategoriesScope]
    fun provideCategoryViewModel(categoryRepository: CategoryRepository) =
        CategoryViewModel(repository = categoryRepository)
}