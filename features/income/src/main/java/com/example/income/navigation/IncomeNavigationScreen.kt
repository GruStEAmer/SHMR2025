package com.example.income.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.income.di.component.IncomeComponent
import com.example.income.di.utils.rememberIncomeComponent
import com.example.income.ui.screens.IncomeAnalysisScreen
import com.example.income.ui.screens.IncomeDetailByIdScreen
import com.example.income.ui.screens.IncomeHistoryScreen
import com.example.income.ui.screens.IncomeScreen

@Composable
fun IncomeNavigationScreen(
    component: IncomeComponent = rememberIncomeComponent(),
    navController: NavHostController = rememberNavController()
) {
    val factory = component.viewModelFactory()

    NavHost(
        navController = navController,
        startDestination = IncomeNavigationModel.Income.route
    ){
        composable(route = IncomeNavigationModel.Income.route){
            IncomeScreen(
                factory = factory,
                navController = navController
            )
        }
        composable(route = IncomeNavigationModel.IncomeHistory.route){
            IncomeHistoryScreen(
                factory = factory,
                navController = navController
            )
        }
        composable(
            route = IncomeNavigationModel.IncomeDetailById.route,
            arguments = listOf(navArgument("incomeId"){ type = NavType.IntType })
        ){ backStackEntry ->
            val incomeId = backStackEntry.arguments?.getInt("incomeId") ?: 0
            IncomeDetailByIdScreen(
                id = incomeId,
                factory = factory,
                navigation = { navController.popBackStack() }
            )
        }
        composable(route = IncomeNavigationModel.IncomeAnalysis.route){
            IncomeAnalysisScreen(
                factory = factory,
                navController = navController
            )
        }

    }
}