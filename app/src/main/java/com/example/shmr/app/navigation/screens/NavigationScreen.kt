package com.example.shmr.app.navigation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shmr.app.navigation.model.NavigationBottomModel
import com.example.shmr.features.categories.ui.screens.CategoryScreen
import com.example.shmr.features.check.ui.screens.CheckScreen
import com.example.shmr.features.expenses.navigation.ExpensesNavigationScreen
import com.example.shmr.features.income.navigation.IncomeNavigationScreen
import com.example.shmr.features.income.ui.screens.IncomeScreen
import com.example.shmr.features.settings.ui.screens.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationScreen(navController:NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { AppBottomBar(navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationBottomModel.CheckNavigation.route,
            modifier = Modifier,
            enterTransition = { fadeIn(tween(700)) },
            exitTransition = { fadeOut(tween(700)) },
            popEnterTransition = { fadeIn(tween(700)) },
            popExitTransition = { fadeOut(tween(700)) },
        ) {
            composable(route = NavigationBottomModel.SettingsNavigation.route) {
                SettingsScreen()
            }
            composable(route = NavigationBottomModel.CheckNavigation.route) {
                CheckScreen()
            }
            composable(route = NavigationBottomModel.IncomeNavigation.route) {
                IncomeNavigationScreen()
            }
            composable(route = NavigationBottomModel.ExpensesNavigation.route) {
                ExpensesNavigationScreen()
            }
            composable(route = NavigationBottomModel.Categories.route) {
                CategoryScreen()
            }
        }
    }
}