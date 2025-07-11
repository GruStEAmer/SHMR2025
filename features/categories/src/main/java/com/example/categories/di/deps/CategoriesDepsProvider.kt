package com.example.categories.di.deps

interface CategoriesDepsProvider {

    val categoriesDeps: CategoriesDeps

    companion object : CategoriesDepsProvider by CategoriesDepsStore
}