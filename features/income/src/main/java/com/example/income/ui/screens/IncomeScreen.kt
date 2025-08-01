package com.example.income.ui.screens

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
import com.example.income.navigation.IncomeNavigationModel
import com.example.model.TransactionUi
import com.example.ui.R
import com.example.ui.components.CircleButton
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.listItems.AccountListItem
import com.example.ui.components.listItems.TransactionListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState

@Composable
fun IncomeScreen(
    factory: ViewModelProvider.Factory,
    navController: NavController
) {
    val incomeViewModel: IncomeViewModel = viewModel(factory = factory)
    val uiState by incomeViewModel.incomeUiState.collectAsState()
    val sumOfTransaction by incomeViewModel.sumIncome.collectAsState()

    LaunchedEffect(Unit) {
        incomeViewModel.getIncomes()
    }
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Доходы сегодня",
                endIcon = R.drawable.ic_history,
                endNavigation = { navController.navigate(IncomeNavigationModel.IncomeHistory.route) }
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
                    "$sumOfTransaction",
                    "RUB"
                )
                when (uiState) {
                    is UiState.Loading -> LoadingScreen()
                    is UiState.Success -> IncomeScreenUi(
                        transactions = (uiState as UiState.Success<List<TransactionUi>>).data,
                        navController = navController
                    )
                    is UiState.Error -> ErrorScreen(
                        message = (uiState as UiState.Error).error.message ?: "Unknown error",
                        reloadData = { incomeViewModel.getIncomes() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
            CircleButton(Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp))
        }
    }
}

@Composable
fun IncomeScreenUi(
    transactions: List<TransactionUi>,
    navController: NavController
) {
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
                clicked = { navController.navigate("income_detail/${it.id}") }
            )
        }
    }
}
