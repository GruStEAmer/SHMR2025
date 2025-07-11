package com.example.categories.di.component

import androidx.lifecycle.ViewModelProvider
import com.example.categories.di.deps.CategoriesDeps
import com.example.categories.di.module.CategoriesDataModule
import com.example.categories.di.module.ViewModelFactoryModule
import com.example.categories.di.module.ViewModelModule
import com.example.categories.di.annotations.CategoriesScope
import dagger.Component

@[CategoriesScope Component(dependencies = [CategoriesDeps::class] ,
    modules = [CategoriesDataModule::class, ViewModelModule::class, ViewModelFactoryModule::class])
]
interface CategoriesComponent {

    fun viewModelFactory(): ViewModelProvider.Factory
    @Component.Builder
    interface Builder{
        fun deps(categoriesDeps: CategoriesDeps): Builder
        fun build(): CategoriesComponent
    }
}