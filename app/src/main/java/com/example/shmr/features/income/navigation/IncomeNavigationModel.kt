package com.example.shmr.features.income.navigation

sealed class IncomeNavigationModel(
    val route: String
) {
    data object Income: IncomeNavigationModel(route = "income")

    data object IncomeHistory: IncomeNavigationModel(route = "income/history")
}