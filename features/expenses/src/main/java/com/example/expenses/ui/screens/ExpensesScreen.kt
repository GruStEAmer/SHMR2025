package com.example.expenses.ui.screens

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expenses.navigation.ExpensesNavigationModel
import com.example.expenses.ui.model.TransactionUi
import com.example.network.model.transaction.TransactionResponse
import com.example.ui.R
import com.example.ui.components.CircleButton
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.listItems.AccountListItem
import com.example.ui.components.listItems.TransactionListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState

@Composable
fun ExpensesScreen(
    factory: ViewModelProvider.Factory,
    navController: NavController
) {
    val expensesViewModel: ExpensesViewModel = viewModel(factory = factory)
    val uiState by expensesViewModel.expensesUiState.collectAsState()
    val sumOfTransaction by expensesViewModel.sumExpenses.collectAsState()

    LaunchedEffect(Unit) {
        expensesViewModel.getTransactions()
    }

    when (uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> ExpensesScreenUi(
            transactions = (uiState as UiState.Success<List<TransactionUi>>).data,
            sum = sumOfTransaction,
            navController = navController
        )
        is UiState.Error -> ErrorScreen(
            message = (uiState as UiState.Error).error.message,
            reloadData = { expensesViewModel.getTransactions() }
        )
    }
}

@Composable
fun ExpensesScreenUi(
    transactions: List<TransactionUi>,
    sum: Double,
    navController: NavController
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Расходы сегодня",
                endIcon = R.drawable.ic_history,
                endNavigation = {
                    navController.navigate(ExpensesNavigationModel.ExpensesHistory.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->
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
                    "RUB"
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
                            categoryId = it.categoryId,
                            categoryName = it.categoryName,
                            emoji = it.categoryEmoji,
                            amount = it.amount,
                            currency = it.accountCurrency,
                            comment = it.comment,
                            clicked = {
                                navController.navigate("expenses_detail/${it.id}")
                            }
                        )
                    }
                }
            }
            CircleButton(Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp)
            )
        }
    }
}