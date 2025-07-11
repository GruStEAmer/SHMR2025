package com.example.categories.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.categories.di.component.CategoriesComponent
import com.example.categories.di.utils.rememberCategoriesComponent
import com.example.categories.ui.screens.CategoryScreen

@Composable
fun CategoriesNavigationScreen(
    navController: NavHostController = rememberNavController(),
    component: CategoriesComponent = rememberCategoriesComponent()
) {
    NavHost(
        navController = navController,
        startDestination = CategoriesNavigationModel.Categories.route
    ){
        composable(route = CategoriesNavigationModel.Categories.route){
            CategoryScreen(
                component.viewModelFactory()
            )
        }
    }
}