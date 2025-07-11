package com.example.expenses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenses.di.component.ExpensesComponent
import com.example.expenses.di.utils.rememberExpensesComponent
import com.example.expenses.ui.screens.ExpensesHistoryScreen
import com.example.expenses.ui.screens.ExpensesScreen

@Composable
fun ExpensesNavigationScreen(
    component: ExpensesComponent = rememberExpensesComponent(),
    navController: NavHostController = rememberNavController()
){
    val factory = component.viewModelFactory()
    NavHost(
        navController = navController,
        startDestination = ExpensesNavigationModel.Expenses.route,
    ) {
        composable(ExpensesNavigationModel.Expenses.route) {
            ExpensesScreen(
                factory = factory,
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
                factory = factory,
                navigation = { navController.popBackStack() }
            )
        }
    }
}