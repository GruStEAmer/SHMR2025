package com.example.expenses.navigation

sealed class ExpensesNavigationModel(
    val route: String
){
    data object Expenses: ExpensesNavigationModel(route = "expenses")

    data object ExpensesHistory: ExpensesNavigationModel(route = "expenses/history")
}

