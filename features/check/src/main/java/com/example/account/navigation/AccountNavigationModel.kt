package com.example.account.navigation

sealed class AccountNavigationModel(
    val route: String
) {
    data object Account: AccountNavigationModel(route = "check")

    data object AccountEdit: AccountNavigationModel(route = "check/edit")
}