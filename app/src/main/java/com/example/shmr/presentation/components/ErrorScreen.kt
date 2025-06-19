package com.example.shmr.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    message: String?,
    reloadData: () -> Unit
){

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        snackBarHostState.showSnackbar(
            message = message ?: "",
            duration = SnackbarDuration.Short
        )
        reloadData()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            snackbar = { snackBarData ->
                Snackbar(
                    shape = RoundedCornerShape(8.dp),
                    containerColor = Color(0xFF333333),
                    contentColor = Color.White
                ) {
                    Text("Error: ${snackBarData.visuals.message}")
                }
            }
        )
    }

}
