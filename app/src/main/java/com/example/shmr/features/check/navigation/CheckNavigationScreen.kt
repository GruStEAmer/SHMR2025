package com.example.shmr.features.check.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shmr.features.check.ui.screens.CheckEditScreen
import com.example.shmr.features.check.ui.screens.CheckScreen
import com.example.shmr.features.check.ui.screens.CheckViewModel

@Composable
fun CheckNavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    val checkViewModel: CheckViewModel = viewModel(factory = CheckViewModel.Companion.Factory)

    NavHost(
        navController = navController,
        startDestination = CheckNavigationModel.Check.route
    ){
        composable(route = CheckNavigationModel.Check.route) {
            CheckScreen(
                navigation = {
                    navController.navigate(CheckNavigationModel.CheckEdit.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                checkViewModel
            )
        }
        composable(route = CheckNavigationModel.CheckEdit.route){
            CheckEditScreen(
                navigation = {
                    navController.popBackStack()
                },
                checkViewModel
            )
        }
    }
}