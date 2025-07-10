package com.example.categories.di.component

import com.example.categories.di.deps.CategoriesDeps
import com.example.categories.di.module.CategoriesDataModule
import com.example.categories.di.scope.CategoriesScope
import dagger.Component

@[CategoriesScope Component(dependencies = [CategoriesDeps::class] , modules = [CategoriesDataModule::class])]
internal interface CategoriesComponent {

    @Component.Builder
    interface Builder{
        fun deps(categoriesDeps: CategoriesDeps): Builder
        fun build(): CategoriesComponent
    }
}