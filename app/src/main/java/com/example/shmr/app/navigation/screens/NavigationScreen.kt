package com.example.shmr.app.navigation.screens

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
import com.example.shmr.app.navigation.model.NavigationTopModel
import com.example.shmr.features.categories.ui.screens.CategoryScreen
import com.example.shmr.features.check.ui.screens.CheckScreen
import com.example.shmr.features.expenses.ui.screens.ExpensesHistoryScreen
import com.example.shmr.features.expenses.ui.screens.ExpensesScreen
import com.example.shmr.features.income.ui.screens.IncomeHistoryScreen
import com.example.shmr.features.income.ui.screens.IncomeScreen
import com.example.shmr.features.settings.ui.screens.SettingsScreen

@Composable
fun NavigationScreen(navController:NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { AppBottomBar(navController) },
        topBar = { AppTopBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationTopModel.Check.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(700)) },
            exitTransition = { fadeOut(tween(700)) },
            popEnterTransition = { fadeIn(tween(700)) },
            popExitTransition = { fadeOut(tween(700)) },
        ) {
            composable(route = NavigationTopModel.Settings.route) {
                SettingsScreen()
            }
            composable(route = NavigationTopModel.Check.route) {
                CheckScreen()
            }
            composable(route = NavigationTopModel.Income.route) {
                IncomeScreen()
            }
            composable(route = NavigationTopModel.Expenses.route) {
                ExpensesScreen()
            }
            composable(route = NavigationTopModel.Categories.route) {
                CategoryScreen()
            }
            composable(route = NavigationTopModel.IncomeHistory.route) {
                IncomeHistoryScreen()
            }
            composable(route = NavigationTopModel.ExpensesHistory.route) {
                ExpensesHistoryScreen()
            }
        }
    }
}