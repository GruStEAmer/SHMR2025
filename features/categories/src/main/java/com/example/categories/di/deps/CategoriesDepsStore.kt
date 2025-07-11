package com.example.categories.di.deps

import kotlin.properties.Delegates.notNull

object CategoriesDepsStore: CategoriesDepsProvider {

    override var categoriesDeps: CategoriesDeps by notNull()
}