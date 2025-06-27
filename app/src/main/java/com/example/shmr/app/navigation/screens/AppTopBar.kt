package com.example.shmr.app.navigation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shmr.app.navigation.model.NavigationTopModel
import com.example.shmr.core.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavHostController) {

    val currentRoute = currentRoute(navController)

    var topBar = NavigationTopModel.navItems.firstOrNull() { it.route == currentRoute }
    if(topBar == null) topBar = NavigationTopModel.Check

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topBar.title,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            topBar.startIcon?.let{
                IconButton(
                    onClick = { navController.popBackStack() },
                ) {
                    Icon(
                        painter = painterResource(topBar.startIcon),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
            }
        },
        actions = {
            topBar.endIcon?.let {
                IconButton(
                    onClick = {
                        topBar.endRoute?.let {
                            navController.navigate(topBar.endRoute)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(topBar.endIcon),
                        contentDescription = "",
                    )
                }
            }
        },
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
fun AppTopBarPreview(){
    AppTopBar(rememberNavController())
}