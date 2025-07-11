package com.example.settings.navigation

sealed class SettingsNavigationModel(
    val route: String
) {
    data object Settings: SettingsNavigationModel(route = "settings")
}