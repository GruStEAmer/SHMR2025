package com.example.expenses.navigation

import android.app.FragmentManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expenses.di.component.ExpensesComponent
import com.example.expenses.di.utils.rememberExpensesComponent
import com.example.expenses.ui.screens.ExpensesDetailByIdScreen
import com.example.expenses.ui.screens.ExpensesHistoryScreen
import com.example.expenses.ui.screens.ExpensesScreen
import com.example.expenses.ui.screens.ExpensesViewModel
import kotlin.math.exp

@Composable
fun ExpensesNavigationScreen(
    component: ExpensesComponent = rememberExpensesComponent(),
    navController: NavHostController = rememberNavController()
){
    val expensesViewModel: ExpensesViewModel = viewModel(factory = component.viewModelFactory())

    NavHost(
        navController = navController,
        startDestination = ExpensesNavigationModel.Expenses.route,
    ) {
        composable(ExpensesNavigationModel.Expenses.route) {
            ExpensesScreen(
                expensesViewModel = expensesViewModel,
                navController = navController
            )
        }
        composable(ExpensesNavigationModel.ExpensesHistory.route) {
            ExpensesHistoryScreen(
                factory = component.viewModelFactory(),
                navigation = { navController.popBackStack() },
            )
        }
        composable(
            route = "expenses_detail/{expensesId}",
            arguments = listOf(navArgument("expensesId"){ type = NavType.IntType })
        ){ backStackEntry ->
            val expensesId = backStackEntry.arguments?.getInt("expensesId") ?: 0
            ExpensesDetailByIdScreen(
                id = expensesId,
                expensesViewModel = expensesViewModel,
                navigation = { navController.popBackStack() }
            )
        }
    }
}