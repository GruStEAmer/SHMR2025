package com.example.shmr.features.income.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shmr.features.expenses.navigation.ExpensesNavigationModel
import com.example.shmr.features.income.ui.screens.IncomeHistoryScreen
import com.example.shmr.features.income.ui.screens.IncomeScreen

@Composable
fun IncomeNavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = IncomeNavigationModel.Income.route
    ){
        composable(route = IncomeNavigationModel.Income.route){
            IncomeScreen(
                navigation = {
                    navController.navigate(IncomeNavigationModel.IncomeHistory.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = IncomeNavigationModel.IncomeHistory.route){
            IncomeHistoryScreen(
                navigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}