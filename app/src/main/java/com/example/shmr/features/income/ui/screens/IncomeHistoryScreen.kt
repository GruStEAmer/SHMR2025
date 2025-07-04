package com.example.shmr.features.income.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shmr.R
import com.example.shmr.StartAccount
import com.example.shmr.core.ui.components.CustomDatePicker
import com.example.shmr.core.ui.components.ErrorScreen
import com.example.shmr.core.ui.components.LoadingScreen
import com.example.shmr.core.ui.components.StartEndSumDetail
import com.example.shmr.core.ui.components.listItems.TransactionListItem
import com.example.shmr.core.ui.navigationBar.AppTopBar
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.transaction.TransactionResponse
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun IncomeHistoryScreen(
    navigation: () -> Unit
) {
    val incomeViewModel: IncomeViewModel = viewModel(factory = IncomeViewModel.Factory)

    val uiState = incomeViewModel.incomeUiState
    val sumTransaction = incomeViewModel.sumIncome

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
    ){ innerPadding ->
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
                    uiState.data,
                )

                is UiState.Error -> ErrorScreen(
                    message = uiState.error.message!!,
                    reloadData = {
                        incomeViewModel.getIncomes(
                            startDate,
                            endDate
                        )
                    }
                )
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
}

@Composable
fun IncomeHistoryScreenUi(
    transactions: List<TransactionResponse>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(
            items = transactions,
            key = { it -> it.id }
        ) {
            TransactionListItem(
                categoryId = it.category.id,
                categoryName = it.category.name,
                emoji = it.category.emoji,
                amount = it.amount,
                currency = StartAccount.CURRENCY,
                comment = it.comment,
                date = it.createdAt
            )
        }
    }
}