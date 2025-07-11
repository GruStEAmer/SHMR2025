package com.example.account.di.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.account.di.component.AccountComponent
import com.example.account.di.deps.AccountComponentViewModel
@Composable
fun rememberAccountComponent(): AccountComponent {
    val componentViewModel: AccountComponentViewModel = viewModel()
    return remember { componentViewModel.accountComponent }
}