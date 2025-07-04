package com.example.shmr.features.check.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shmr.features.check.ui.screens.CheckScreen

@Composable
fun CheckNavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CheckNavigationModel.Check.route
    ){
        composable(route = CheckNavigationModel.Check.route) {
            CheckScreen()
        }
    }
}