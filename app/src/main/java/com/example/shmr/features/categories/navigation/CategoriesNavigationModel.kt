package com.example.shmr.features.categories.navigation

sealed class CategoriesNavigationModel(
    val route: String
) {
    data object Categories: CategoriesNavigationModel(route = "categories")
}