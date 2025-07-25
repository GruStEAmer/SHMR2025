package com.example.income.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.graphics.ui.PieChart
import com.example.model.CategoryUi
import com.example.model.TransactionUi
import com.example.ui.R
import com.example.ui.components.CustomDatePicker
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.listItems.AnalysisDetail
import com.example.ui.components.listItems.AnalysisListItem
import com.example.ui.components.listItems.TransactionListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.roundToInt

@Composable
fun IncomeAnalysisScreen(
    factory: ViewModelProvider.Factory,
    navController: NavController
) {
    val incomeViewModel: IncomeAnalysisViewModel = viewModel(factory = factory)
    val uiState by incomeViewModel.incomeUiState.collectAsState()
    val sumTransaction by incomeViewModel.sumIncome.collectAsState()

    var startDate by remember { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(startDate, endDate) {
        incomeViewModel.getIncomes(startDate = startDate, endDate = endDate)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Анализ",
                startIcon = R.drawable.ic_return,
                startNavigation = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider()
            AnalysisListItem(
                name = "Период: начало",
                date = startDate.toString(),
                changeDate = { showStartDatePicker = true }
            )
            HorizontalDivider()
            AnalysisListItem(
                name = "Период: конец",
                date = endDate.toString(),
                changeDate = { showEndDatePicker = true }
            )

            HorizontalDivider()
            ListItem(
                headlineContent = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Сумма",
                            fontSize = 16.sp
                        )
                        Text(
                            text = "$sumTransaction ₽"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
            HorizontalDivider()

            when (uiState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Success -> IncomeAnalysisScreenUi(
                    transactions = (uiState as UiState.Success<List<Pair<CategoryUi,Double>>>).data,
                    sum = sumTransaction
                )
                is UiState.Error -> ErrorScreen(
                    message = (uiState as UiState.Error).error.message ?: "Unknown error",
                    reloadData = {
                        incomeViewModel.getIncomes(
                            startDate = startDate,
                            endDate = endDate
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
fun IncomeAnalysisScreenUi(
    transactions: List<Pair<CategoryUi, Double>>,
    sum: Double
) {
    PieChart(
        chartDataList =
            transactions.map { it -> Pair(it.first.name, it.second / sum * 100)}
    )

    HorizontalDivider()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = transactions,
            key = { it -> it.first.id }
        ) {
            AnalysisDetail(
                categoryId = it.first.id,
                categoryName = it.first.name,
                emoji = it.first.emoji,
                amount = it.second.toString(),
                currency = "RUB",
                percent = "${(it.second / sum * 100).roundToInt()}%",
            )
        }
    }
}