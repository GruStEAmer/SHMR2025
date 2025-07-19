package com.example.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Green

@Composable
fun ErrorScreen(
    message: String?,
    reloadData: () -> Unit,
    modifier:Modifier = Modifier
){
    var counter by remember { mutableIntStateOf(0) }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        snackBarHostState.showSnackbar(
            message = message ?: "",
            duration = SnackbarDuration.Short
        )
        if(message == "Нет подключение к интернету" || (message == "500" && counter < 3)) {
            reloadData()
        }
        counter++
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ){
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            snackbar = { snackBarData ->
                Snackbar(
                    shape = RoundedCornerShape(8.dp),
                    containerColor = Green,
                    contentColor = Color.Black
                ) {
                    Text("Error: ${snackBarData.visuals.message}")
                }
            }
        )
    }

}
