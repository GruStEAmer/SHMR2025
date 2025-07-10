package com.example.shmr.navigation.screens

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shmr.navigation.model.NavigationBottomModel
import com.example.shmr.core.ui.theme.Green
import com.example.shmr.core.ui.theme.LightGreen

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val currentRoute = currentRoute(navController)
    NavigationBar {
        NavigationBottomModel.navItems.forEach { item ->
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