package com.example.account.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shmr.domain.model.account.AccountResponse
import com.example.ui.R
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState

@Composable
fun AccountEditScreen(
    navigation: () -> Unit,
    accountViewModel: AccountViewModel
) {
    val uiState: UiState<AccountResponse> by accountViewModel.checkUiState.collectAsState()

    when(uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> AccountEditScreenUi(
            account = (uiState as UiState.Success<AccountResponse>).data,
            onSaveClick = { name, balance ->
                accountViewModel.putAccount(name, balance)
                navigation()
            },
            navigation = navigation
        )
        is UiState.Error -> ErrorScreen(
            message = (uiState as UiState.Error).error.message ?: "Unknown error",
            reloadData = { accountViewModel.getAccountInfo() }
        )
    }
}

@Composable
fun AccountEditScreenUi(
    account: AccountResponse,
    onSaveClick: (String, String) -> Unit,
    navigation: () -> Unit = {}
) {
    var name by remember { mutableStateOf(account.name) }
    var balance by remember { mutableStateOf(account.balance) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Мой счет",
                startIcon = R.drawable.ic_cross,
                startNavigation = navigation,
                endIcon = R.drawable.ic_mark,
                endNavigation = {
                    onSaveClick(name, balance)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ListItem(
                headlineContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            label = { Text("Название") }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        TextField(
                            value = balance,
                            onValueChange = { balance = it },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            label = { Text("Баланс") }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = "Аккаунт",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                },
                trailingContent = {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(Color.Red)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Удалить",
                            tint = Color.White
                        )
                    }
                }
            )

            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Удалить счет", color = Color.White)
            }
        }
    }
}