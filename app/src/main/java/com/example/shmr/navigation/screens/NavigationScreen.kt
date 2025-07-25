package com.example.shmr.navigation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.account.navigation.AccountNavigationScreen
import com.example.categories.navigation.CategoriesNavigationScreen
import com.example.expenses.navigation.ExpensesNavigationScreen
import com.example.income.navigation.IncomeNavigationScreen
import com.example.settings.navigation.SettingsNavigationScreen
import com.example.shmr.navigation.model.NavigationBottomModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationScreen(navController:NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { AppBottomBar(navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationBottomModel.CategoriesNavigation.route,
            modifier = Modifier,
            enterTransition = { fadeIn(tween(700)) },
            exitTransition = { fadeOut(tween(700)) },
            popEnterTransition = { fadeIn(tween(700)) },
            popExitTransition = { fadeOut(tween(700)) },
        ) {
            composable(route = NavigationBottomModel.SettingsNavigation.route) {
                SettingsNavigationScreen()
            }
            composable(route = NavigationBottomModel.AccountNavigation.route) {
                AccountNavigationScreen()
            }
            composable(route = NavigationBottomModel.IncomeNavigation.route) {
                IncomeNavigationScreen()
            }
            composable(route = NavigationBottomModel.ExpensesNavigation.route) {
                ExpensesNavigationScreen()
            }
            composable(route = NavigationBottomModel.CategoriesNavigation.route) {
                CategoriesNavigationScreen()
            }
        }
    }
}