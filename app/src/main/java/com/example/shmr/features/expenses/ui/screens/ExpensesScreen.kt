package com.example.shmr.features.expenses.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shmr.R
import com.example.shmr.StartAccount
import com.example.shmr.core.ui.components.CircleButton
import com.example.shmr.core.ui.components.ErrorScreen
import com.example.shmr.core.ui.components.LoadingScreen
import com.example.shmr.core.ui.components.listItems.AccountListItem
import com.example.shmr.core.ui.components.listItems.TransactionListItem
import com.example.shmr.core.ui.navigationBar.AppTopBar
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.transaction.TransactionResponse


@Composable
fun ExpensesScreen(
    navigation: () -> Unit
) {
    val expensesViewModel: ExpensesViewModel = viewModel(factory = ExpensesViewModel.Factory)
    val uiState = expensesViewModel.expensesUiState
    val sumOfTransaction = expensesViewModel.sumExpenses

    when (uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> ExpensesScreenUi(
            transactions = uiState.data,
            sum = sumOfTransaction,
            navigation = navigation
        )
        is UiState.Error -> ErrorScreen(
            message = uiState.error.message,
            reloadData = { expensesViewModel.getTransactions() }
        )
    }

}

@Composable()
fun ExpensesScreenUi(
    transactions: List<TransactionResponse>,
    sum: Double,
    navigation: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Расходы сегодня",
                endIcon = R.drawable.ic_history,
                endNavigation = navigation
            )
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AccountListItem(
                    "Всего",
                    "$sum",
                    StartAccount.CURRENCY
                )

                HorizontalDivider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        items = transactions,
                        key = { it -> it.id }
                    ) {
                        TransactionListItem(
                            categoryId = it.category.id,
                            categoryName = it.category.name,
                            emoji = it.category.emoji,
                            amount = it.amount,
                            currency = it.account.currency,
                            comment = it.comment
                        )
                    }
                }
            }
            CircleButton(Modifier.align(Alignment.BottomEnd))
        }
    }
}