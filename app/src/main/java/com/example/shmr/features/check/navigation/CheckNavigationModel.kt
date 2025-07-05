package com.example.shmr.features.check.navigation

sealed class CheckNavigationModel(
    val route: String
) {
    data object Check: CheckNavigationModel(route = "check")

    data object CheckEdit: CheckNavigationModel(route = "check/edit")
}