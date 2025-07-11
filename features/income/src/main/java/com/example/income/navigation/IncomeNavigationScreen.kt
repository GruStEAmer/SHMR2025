package com.example.income.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.income.di.component.IncomeComponent
import com.example.income.di.utils.rememberIncomeComponent
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
                factory = factory,
                navigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}