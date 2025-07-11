package com.example.account.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.account.di.component.AccountComponent
import com.example.account.di.utils.rememberAccountComponent
import com.example.account.ui.screens.AccountEditScreen
import com.example.account.ui.screens.AccountScreen
import com.example.account.ui.screens.AccountViewModel

@Composable
fun AccountNavigationScreen(
    component: AccountComponent = rememberAccountComponent(),
    navController: NavHostController = rememberNavController()
) {

    val accountViewModel: AccountViewModel = viewModel(factory = component.viewModelFactory())

    NavHost(
        navController = navController,
        startDestination = AccountNavigationModel.Account.route
    ){
        composable(route = AccountNavigationModel.Account.route) {
            AccountScreen(
                navigation = {
                    navController.navigate(AccountNavigationModel.AccountEdit.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                accountViewModel
            )
        }
        composable(route = AccountNavigationModel.AccountEdit.route){
            AccountEditScreen(
                navigation = {
                    navController.popBackStack()
                },
                accountViewModel
            )
        }
    }
}