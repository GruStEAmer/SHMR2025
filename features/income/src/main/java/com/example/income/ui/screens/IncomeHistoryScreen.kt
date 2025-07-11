package com.example.income.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.income.data.model.TransactionResponse
import com.example.ui.R
import com.example.ui.components.CustomDatePicker
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.StartEndSumDetail
import com.example.ui.components.listItems.TransactionListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun IncomeHistoryScreen(
    factory: ViewModelProvider.Factory,
    navigation: () -> Unit
) {
    val incomeViewModel: IncomeViewModel = viewModel(factory = factory)
    val uiState by incomeViewModel.incomeUiState.collectAsState()
    val sumTransaction by incomeViewModel.sumIncome.collectAsState()

    var startDate by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(startDate, endDate) {
        incomeViewModel.getIncomes(startDate, endDate)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Моя история",
                startIcon = R.drawable.ic_return,
                endIcon = R.drawable.ic_analysis,
                startNavigation = navigation
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            StartEndSumDetail(
                startDate = startDate,
                endDate = endDate,
                changeStartDatePicker = { showStartDatePicker = true },
                changeEndDatePicker = { showEndDatePicker = true },
                balance = sumTransaction.toString()
            )

            when (uiState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Success -> IncomeHistoryScreenUi(
                    (uiState as UiState.Success<List<TransactionResponse>>).data,
                )
                is UiState.Error -> ErrorScreen(
                    message = (uiState as UiState.Error).error.message ?: "Unknown error",
                    reloadData = {
                        incomeViewModel.getIncomes(
                            startDate,
                            endDate
                        )
                    }
                )
            }
        }
    }

    if (showStartDatePicker) {
        CustomDatePicker(
            onDateSelected = { timestamp ->
                timestamp?.let {
                    startDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
                showStartDatePicker = false
            },
            onDismiss = { showStartDatePicker = false }
        )
    }

    if (showEndDatePicker) {
        CustomDatePicker(
            onDateSelected = { timestamp ->
                timestamp?.let {
                    endDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                }
                showEndDatePicker = false
            },
            onDismiss = { showEndDatePicker = false }
        )
    }
}

@Composable
fun IncomeHistoryScreenUi(
    transactions: List<TransactionResponse>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
                currency = "RUB",
                comment = it.comment,
                date = it.createdAt
            )
        }
    }
}