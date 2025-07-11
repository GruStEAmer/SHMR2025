package com.example.ui.navigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.ui.theme.Green

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopBar(
    title: String,
    startIcon: Int? = null,
    endIcon: Int? = null,
    startNavigation: () -> Unit = {},
    endNavigation: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            startIcon?.let{
                IconButton(
                    onClick = { startNavigation() },
                ) {
                    Icon(
                        painter = painterResource(startIcon),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                }
            }
        },
        actions = {
            endIcon?.let {
                IconButton(
                    onClick = {
                        endNavigation()
                    }
                ) {
                    Icon(
                        painter = painterResource(endIcon),
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