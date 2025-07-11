package com.example.categories.di.deps

import androidx.lifecycle.ViewModel
import com.example.categories.di.component.CategoriesComponent
import com.example.categories.di.component.DaggerCategoriesComponent

internal class CategoryComponentViewModel : ViewModel() {
    val categoriesComponent: CategoriesComponent by lazy {
        DaggerCategoriesComponent.builder()
            .deps(CategoriesDepsProvider.categoriesDeps)
            .build()
    }
}