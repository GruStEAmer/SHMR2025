package com.example.shmr.navigation.model

import com.example.shmr.R

sealed class NavigationBottomModel(
    val route: String,
    val iconPassive: Int,
    val text: String,
){
    data object SettingsNavigation : NavigationBottomModel("settings" ,  R.drawable.ic_settings_passive, "Настройки")
    data object IncomeNavigation : NavigationBottomModel("income" ,R.drawable.ic_income_passive, "Доходы")
    data object AccountNavigation : NavigationBottomModel("account",  R.drawable.ic_check_passive, "Счет")
    data object ExpensesNavigation : NavigationBottomModel("expenses", R.drawable.ic_expenses_passive, "Расходы")
    data object CategoriesNavigation : NavigationBottomModel("categories",  R.drawable.ic_category_passive, "Cтатьи")
    companion object {
        val navItems = listOf( ExpensesNavigation, IncomeNavigation, AccountNavigation , CategoriesNavigation , SettingsNavigation)
    }
}