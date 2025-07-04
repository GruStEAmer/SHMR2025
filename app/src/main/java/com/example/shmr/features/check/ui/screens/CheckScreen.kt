package com.example.shmr.features.check.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shmr.R
import com.example.shmr.core.ui.components.CircleButton
import com.example.shmr.core.ui.components.ErrorScreen
import com.example.shmr.core.ui.components.LoadingScreen
import com.example.shmr.core.ui.components.listItems.AccountListItem
import com.example.shmr.core.ui.navigationBar.AppTopBar
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.account.AccountResponse

@Composable
fun CheckScreen() {
    val checkViewModel: CheckViewModel = viewModel(factory = CheckViewModel.Companion.Factory)
    val uiState:UiState<AccountResponse> by checkViewModel.checkUiState.collectAsState()
    when(uiState){
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> CheckScreenUi((uiState as UiState.Success<AccountResponse>).data)
        is UiState.Error -> ErrorScreen(message =  (uiState as UiState.Error).error.message, reloadData = { checkViewModel.getAccountInfo() })
    }
}

@Composable
fun CheckScreenUi(account: AccountResponse) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Мой счет",
                endIcon = R.drawable.ic_edit
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
            CircleButton(Modifier.align(Alignment.BottomEnd))
        }
    }
}

@Preview
@Composable
fun CheckScreenPreview(){
    CheckScreen()
}