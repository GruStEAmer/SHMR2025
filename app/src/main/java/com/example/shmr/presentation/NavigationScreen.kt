package com.example.shmr.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shmr.presentation.model.NavigationModel
import com.example.shmr.presentation.screens.CategoryScreen
import com.example.shmr.presentation.screens.CheckScreen
import com.example.shmr.presentation.screens.ExpensesScreen
import com.example.shmr.presentation.screens.IncomeScreen
import com.example.shmr.presentation.screens.SettingsScreen
import com.example.shmr.presentation.theme.Green
import com.example.shmr.presentation.theme.LightGreen

@Composable
fun NavigationScreen(navController:NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { AppBottomBar(navController) },
        topBar = { AppTopBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationModel.Check.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(700)) },
            exitTransition = { fadeOut(tween(700)) },
            popEnterTransition = { fadeIn(tween(700)) },
            popExitTransition = { fadeOut(tween(700)) },
        ) {
            composable(route = NavigationModel.Settings.route) {
                SettingsScreen()
            }
            composable(route = NavigationModel.Check.route){
                CheckScreen()
            }
            composable(route = NavigationModel.Income.route){
                IncomeScreen()
            }
            composable(route = NavigationModel.Expenses.route) {
                ExpensesScreen()
            }
            composable(route = NavigationModel.Categories.route){
                CategoryScreen()
            }
        }
    }
}

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationModel.navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
                          },
                icon = {
                    Icon(
                        painter = painterResource(item.iconPassive),
                        contentDescription = "",
                    )
                        },
                label = { Text(item.text) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    indicatorColor = LightGreen
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    
    var titleTop = "Undefined"
    var iconTopBar:Int? = null
    NavigationModel.navItems.forEach {
        if(it.route == currentRoute) {
            titleTop = it.title
            iconTopBar = it.iconTopBar
        }
    }
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = titleTop,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                iconTopBar?.let {
                    Icon(
                        painter = painterResource(iconTopBar),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .clickable{},
                        tint = Color.Unspecified
                    )
                }
            }
                },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Green,
        )
    )
}
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview
@Composable
fun AppBottomBarPreview(){
    AppBottomBar(rememberNavController())
}

@Preview
@Composable
fun AppTopBarPreview(){
    AppTopBar(rememberNavController())
}