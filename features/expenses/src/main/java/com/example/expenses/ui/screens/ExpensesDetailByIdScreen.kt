package com.example.expenses.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expenses.ui.model.TransactionUi
import com.example.network.model.transaction.TransactionRequest
import com.example.ui.R
import com.example.ui.components.CustomDatePicker
import com.example.ui.components.CustomTimePicker
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.listItems.DetailListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun ExpensesDetailByIdScreen(
    id: Int = -1,
    factory: ViewModelProvider.Factory,
    navigation: () -> Unit
){
    val expensesViewModel: ExpensesDetailByIdViewModel = viewModel(factory = factory)
    val transaction by expensesViewModel.transaction.collectAsState()

    if(id != -1){
        LaunchedEffect(id) {
            expensesViewModel.getTransactionById(id)
        }
    }
    when(transaction){
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> ExpensesDetailByIdScreenUi(
            transaction = (transaction as UiState.Success<TransactionUi>).data,
            putTransaction = { x, y->
                expensesViewModel.putTransactionById(x,y)},
            navigation = navigation
        )
        is UiState.Error -> ErrorScreen(
            message = (transaction as UiState.Error).error.message,
            reloadData = { expensesViewModel.getTransactionById(id) }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesDetailByIdScreenUi(
    transaction: TransactionUi,
    putTransaction: (Int, TransactionRequest) -> Unit,
    navigation: () -> Unit
) {
    var check by remember{ mutableStateOf("") }
    var category by remember{ mutableStateOf("") }
    var amount by remember{ mutableStateOf("") }
    var date by remember{ mutableStateOf(LocalDate.now()) }
    var time by remember{ mutableStateOf(LocalTime.now()) }
    var comment by remember{ mutableStateOf("")  }

    var showDatePicker by remember { mutableStateOf(false)}
    var showTimePicker by remember { mutableStateOf(false) }

    transaction.let{ transaction ->
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
                    putTransaction(
                        transaction.id,
                        TransactionRequest(
                            accountId = transaction.accountId,
                            categoryId = transaction.categoryId,
                            amount = amount,
                            transactionDate = "${date}T$time:00.000Z",
                            comment = comment
                        )
                    )
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
            DetailListItem(
                name = "Счет",
                text = check,
            )
            HorizontalDivider()
            DetailListItem(
                name = "Статья",
                text = category,
            )
            HorizontalDivider()
            ListItem(
                headlineContent = {
                    Text(text = "Сумма", modifier = Modifier)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                trailingContent = {
                    TextField(
                        value = amount,
                        onValueChange = { it-> amount = it},
                        modifier = Modifier
                    )
                }
            )
            HorizontalDivider()
            DetailListItem(
                name = "Дата",
                text = "$date",
                clicked = { showDatePicker = true},
            )
            HorizontalDivider()
            DetailListItem(
                name = "Время",
                text = "$time",
                clicked = { showTimePicker = true },
                )
            HorizontalDivider()
            ListItem(
                headlineContent = {
                    TextField(
                        value = comment,
                        onValueChange = {it -> comment = it},
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            )
        }
        if (showDatePicker) {
            CustomDatePicker(
                onDateSelected = { timestamp ->
                    timestamp?.let {
                        date = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
        if (showTimePicker) {
            CustomTimePicker(
                onConfirm = { it->
                    time = LocalTime.of(it.hour, it.minute)
                    showTimePicker = false
                },
                onDismiss = {
                    showTimePicker = false
                }
            )
        }
    }
}