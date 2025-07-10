package com.example.categories.navigation

sealed class CategoriesNavigationModel(
    val route: String
) {
    data object Categories: CategoriesNavigationModel(route = "categories")
}