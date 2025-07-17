package com.example.income.navigation

sealed class IncomeNavigationModel(
    val route: String
) {
    data object Income: IncomeNavigationModel(route = "income")

    data object IncomeHistory: IncomeNavigationModel(route = "income/history")

    data object IncomeDetailById: IncomeNavigationModel(route = "income_detail/{incomeId}")
}