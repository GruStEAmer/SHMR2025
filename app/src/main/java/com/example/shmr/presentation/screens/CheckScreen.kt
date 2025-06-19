package com.example.shmr.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shmr.R
import com.example.shmr.domain.model.account.AccountResponse
import com.example.shmr.presentation.components.CircleButton
import com.example.shmr.presentation.components.ErrorScreen
import com.example.shmr.presentation.components.LoadingScreen
import com.example.shmr.presentation.listItems.AccountListItem
import com.example.shmr.presentation.state.UiState
import com.example.shmr.presentation.viewModels.CheckViewModel
import com.example.shmr.presentation.viewModels.CheckViewModel.Companion.Factory

@Composable
fun CheckScreen() {
    val checkViewModel: CheckViewModel = viewModel(factory = Factory)
    val uiState:UiState<AccountResponse> = checkViewModel.checkUiState

    when(uiState){
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> CheckScreenUi(uiState.data)
        is UiState.Error -> ErrorScreen(message =  uiState.error.message, reloadData = { checkViewModel.getAccountInfo() })
    }
}

@Composable
fun CheckScreenUi(account: AccountResponse) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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

@Preview
@Composable
fun CheckScreenPreview(){
    CheckScreen()
}