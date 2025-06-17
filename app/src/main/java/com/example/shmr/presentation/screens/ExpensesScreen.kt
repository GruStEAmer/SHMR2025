package com.example.shmr.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shmr.accountExpenses
import com.example.shmr.listExpensesTransaction
import com.example.shmr.presentation.components.CircleButton
import com.example.shmr.presentation.listItems.AccountListItem
import com.example.shmr.presentation.listItems.TransactionListItem


@Composable
fun ExpensesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AccountListItem(
                accountExpenses.name,
                accountExpenses.balance,
                accountExpenses.currency
            )

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    items = listExpensesTransaction,
                    key = { it -> it.id }
                ) {
                    TransactionListItem(
                        name = it.name,
                        emoji = it.emoji,
                        categoryId = it.categoryId,
                        amount = it.amount,
                        comment = it.comment,
                        accountExpenses.currency
                    )
                }
            }
        }
        CircleButton(Modifier.align(Alignment.BottomEnd))
    }
}

@Preview
@Composable
fun ExpensesScreenPreview() {
    ExpensesScreen()
}