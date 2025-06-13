package com.example.shmr.ui.model

import com.example.shmr.R

sealed class NavigationModel(
    val route: String,
    val title: String,
    val iconPassive: Int,
    val text: String,
    val iconTopBar: Int?
){
    data object Settings : NavigationModel("settings" , "Настройки", R.drawable.ic_settings_passive, "Настройки", null)
    data object Income : NavigationModel("income" , "Доходы сегодня",R.drawable.ic_income_passive, "Доходы", R.drawable.ic_history)
    data object Check : NavigationModel("check", "Мой счет", R.drawable.ic_check_passive, "Счет", R.drawable.ic_edit)
    data object Expenses : NavigationModel("expenses", "Расходы сегодня", R.drawable.ic_expenses_passive, "Расходы", R.drawable.ic_history)
    data object Articles : NavigationModel("articles", "Мои статьи", R.drawable.ic_articles_passive, "Cтатьи",null)
    companion object {
        val navItems = listOf( Expenses, Income, Check, Articles, Settings)
    }
}
