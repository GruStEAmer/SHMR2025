package com.example.shmr.app.navigation.model

import com.example.shmr.R

sealed class NavigationBottomModel(
    val route: String,
    val iconPassive: Int,
    val text: String,
){
    data object Settings : NavigationBottomModel("settings" ,  R.drawable.ic_settings_passive, "Настройки")
    data object Income : NavigationBottomModel("income" ,R.drawable.ic_income_passive, "Доходы")
    data object Check : NavigationBottomModel("check",  R.drawable.ic_check_passive, "Счет")
    data object Expenses : NavigationBottomModel("expenses", R.drawable.ic_expenses_passive, "Расходы")
    data object Categories : NavigationBottomModel("categories",  R.drawable.ic_category_passive, "Cтатьи")
    companion object {
        val navItems = listOf( Expenses, Income, Check, Categories, Settings)
    }
}

sealed class NavigationTopModel(
    val route: String,
    val title: String,
    val startIcon: Int?,
    val endIcon: Int?,
    val endRoute: String?,
){
    data object Settings: NavigationTopModel("settings", "Настройки", null, null, null)
    data object Income: NavigationTopModel("income", "Доходы сегодня", null, R.drawable.ic_history ,"income/history")
    data object Check: NavigationTopModel("check" , "Мой счет", null, R.drawable.ic_edit, null)
    data object Expenses : NavigationTopModel("expenses" , "Расходы сегодня", null, R.drawable.ic_history, "expenses/history")
    data object Categories : NavigationTopModel("categories" , "Мои статьи", null , null, null)
    data object IncomeHistory: NavigationTopModel("income/history", "Моя история", R.drawable.ic_return, R.drawable.ic_analysis, null)
    data object ExpensesHistory: NavigationTopModel("expenses/history", "Моя история", R.drawable.ic_return, R.drawable.ic_analysis, null)

    companion object {
        val navItems = listOf(
            Expenses,
            Income,
            Check,
            Categories,
            Settings,
            IncomeHistory,
            ExpensesHistory
        )
    }
}