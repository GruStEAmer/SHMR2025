package com.example.shmr.features.expenses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shmr.features.expenses.ui.screens.ExpensesHistoryScreen
import com.example.shmr.features.expenses.ui.screens.ExpensesScreen

@Composable
fun ExpensesNavigationScreen(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = ExpensesNavigationModel.Expenses.route,
    ) {
        composable(ExpensesNavigationModel.Expenses.route) {
            ExpensesScreen(
                navigation = {
                    navController.navigate(ExpensesNavigationModel.ExpensesHistory.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(ExpensesNavigationModel.ExpensesHistory.route) {
            ExpensesHistoryScreen(
                navigation = { navController.popBackStack() }
            )
        }
    }
}