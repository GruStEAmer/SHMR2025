package com.example.shmr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shmr.accountCheck
import com.example.shmr.accountCurrency
import com.example.shmr.ui.components.CircleButton
import com.example.shmr.ui.listItems.AccountListItem

@Composable
fun CheckScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AccountListItem(
                name = accountCheck.name,
                balance = accountCheck.balance,
                currency = accountCheck.currency,
                emoji = "\uD83D\uDCB0"
            )
            HorizontalDivider()
            AccountListItem(
                name = accountCurrency.name,
                balance = accountCurrency.balance,
                currency = accountCurrency.currency,
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