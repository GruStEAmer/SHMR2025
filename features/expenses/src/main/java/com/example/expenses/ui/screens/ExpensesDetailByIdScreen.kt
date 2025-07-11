package com.example.expenses.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.R
import com.example.ui.components.listItems.AccountListItem
import com.example.ui.navigationBar.AppTopBar

@Composable
fun ExpensesDetailByIdScreen(
    id: Int,
    expensesViewModel: ExpensesViewModel,
    navigation: () -> Unit
) {
    var check by remember{ mutableStateOf("") }
    var category by remember{ mutableStateOf("") }
    var amount by remember{ mutableStateOf("") }
    var date by remember{ mutableStateOf("") }
    var time by remember{ mutableStateOf("") }
    var comment by remember{ mutableStateOf("")  }

    expensesViewModel.getTransactionById(id)?.let { transaction ->
        check = transaction.accountName
        category = transaction.categoryName
        amount = transaction.amount
        date = transaction.date
        time = transaction.time
        comment = transaction.comment ?: ""
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Мои расходы",
                startIcon = R.drawable.ic_cross,
                startNavigation = navigation,
                endIcon = R.drawable.ic_mark,
                endNavigation = {
                    navigation()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AccountListItem(
                name = "Счет",
                balance = check,
            )
            HorizontalDivider()
            AccountListItem(
                name = "Статья",
                balance = category,
            )
            HorizontalDivider()
            AccountListItem(
                name = "Сумма",
                balance = amount,
            )
            HorizontalDivider()
            AccountListItem(
                name = "Дата",
                balance = date,
            )
            HorizontalDivider()
            AccountListItem(
                name = "Время",
                balance = time,
            )
            HorizontalDivider()
            AccountListItem(
                name = comment,
                balance = "",
            )
        }
    }
}