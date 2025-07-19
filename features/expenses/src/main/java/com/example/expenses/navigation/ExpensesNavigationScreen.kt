package com.example.expenses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expenses.di.component.ExpensesComponent
import com.example.expenses.di.utils.rememberExpensesComponent
import com.example.expenses.ui.screens.ExpensesAnalysisScreen
import com.example.expenses.ui.screens.ExpensesDetailByIdScreen
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
                navController = navController
            )
        }
        composable(ExpensesNavigationModel.ExpensesHistory.route) {
            ExpensesHistoryScreen(
                factory = factory,
                navController = navController
            )
        }
        composable(ExpensesNavigationModel.ExpensesAnalysis.route) {
            ExpensesAnalysisScreen(
                factory = factory,
                navController = navController
            )
        }
        composable(
            route = ExpensesNavigationModel.ExpensesDetailById.route,
            arguments = listOf(navArgument("expensesId"){ type = NavType.IntType })
        ){ backStackEntry ->
            val expensesId = backStackEntry.arguments?.getInt("expensesId") ?: 0
            ExpensesDetailByIdScreen(
                id = expensesId,
                factory = factory,
                navigation = { navController.popBackStack() }
            )
        }
    }
}