package com.example.shmr

import com.example.shmr.presentation.model.AccountStateUi
import com.example.shmr.presentation.model.CategoryUi
import com.example.shmr.presentation.model.TransactionUi

val listCategory = listOf<CategoryUi>(
    CategoryUi(1, "Аренда квартиры", "\uD83C\uDFE1"),
    CategoryUi(2, "Одежда", "\uD83D\uDC57"),
    CategoryUi(3, "На собачку", "\uD83D\uDC36"),
    CategoryUi(4, "На собачку", "\uD83D\uDC36"),
    CategoryUi(5, "Ремонт квартиры", "РК"),
    CategoryUi(6, "Продукты", "\uD83C\uDF6D"),
    CategoryUi(7, "Спортзал", "\uD83C\uDFCB\uFE0F\u200D♂\uFE0F"),
    CategoryUi(8, "Медицина", "\uD83D\uDC8A"),
)

val listExpensesTransaction = listOf<TransactionUi>(
    TransactionUi(1, "Аренда квартиры", "\uD83C\uDFE1", 1, "100 000", null),
    TransactionUi(2, "Одежда", "\uD83D\uDC57", 2, "100 000", null),
    TransactionUi(3, "На собачку", "\uD83D\uDC36", 3, "100 000", "Джек"),
    TransactionUi(4, "На собачку", "\uD83D\uDC36", 4,"100 000", "Энни"),
    TransactionUi(5, "Ремонт квартиры", "РК" , 5, "100 000", null),
    TransactionUi(6, "Продукты", "\uD83C\uDF6D", 6, "100 000", null),
    TransactionUi(7, "Спортзал", "\uD83C\uDFCB\uFE0F\u200D♂\uFE0F", 7, "100 000", null),
    TransactionUi(8, "Медицина", "\uD83D\uDC8A", 8, "100 000", null),
)

val listIncomeTransaction = listOf<TransactionUi>(
    TransactionUi(1, "Зарплата", null, 1, "500 000", null) ,
    TransactionUi(2, "Подработка", null, 2, "100 000", null)
)

val accountExpenses = AccountStateUi(
    1,
    "Всего",
    "436 558",
    "RUB"
)
val accountIncome = AccountStateUi(
    2,
    "Всего",
    "600 000",
    "RUB"
)
val accountCheck = AccountStateUi(0, "Баланс", "-670 000", "RUB")

val accountCurrency = AccountStateUi(0, "Валюта", "" , "RUB")

