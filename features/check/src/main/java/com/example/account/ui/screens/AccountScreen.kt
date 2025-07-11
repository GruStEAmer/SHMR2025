package com.example.account.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.account.data.model.AccountResponse
import com.example.ui.R
import com.example.ui.components.CircleButton
import com.example.ui.components.ErrorScreen
import com.example.ui.components.LoadingScreen
import com.example.ui.components.listItems.AccountListItem
import com.example.ui.navigationBar.AppTopBar
import com.example.ui.state.UiState

@Composable
fun AccountScreen(
    navigation: () -> Unit,
    accountViewModel: AccountViewModel
) {
    val uiState:UiState<AccountResponse> by accountViewModel.checkUiState.collectAsState()
    when(uiState){
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> AccountScreenUi(
            account = (uiState as UiState.Success<AccountResponse>).data,
            navigation = navigation
        )
        is UiState.Error -> ErrorScreen(message =  (uiState as UiState.Error).error.message, reloadData = { accountViewModel.getAccountInfo() })
    }
}

@Composable
fun AccountScreenUi(
    account: AccountResponse,
    navigation: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Мой счет",
                endIcon = R.drawable.ic_edit,
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
            ) {
                AccountListItem(
                    name = account.name,
                    balance = account.balance,
                    currency = account.currency,
                    emoji = stringResource(R.string.emoji_money)
                )
                HorizontalDivider()
                AccountListItem(
                    name = stringResource(R.string.check_currency),
                    balance = "",
                    currency = account.currency,
                )
            }
            CircleButton(Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp)
            )
        }
    }
}
