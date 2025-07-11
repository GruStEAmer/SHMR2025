package com.example.settings.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.settings.ui.screens.SettingsScreen

@Composable
fun SettingsNavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SettingsNavigationModel.Settings.route
    ){
        composable(route = SettingsNavigationModel.Settings.route){
            SettingsScreen()
        }
    }
}