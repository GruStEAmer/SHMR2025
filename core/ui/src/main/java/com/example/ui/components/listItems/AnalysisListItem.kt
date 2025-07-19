package com.example.ui.components.listItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.Green

@Composable
fun AnalysisListItem(
    name: String,
    date: String,
    changeDate: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = name
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { changeDate() },
        trailingContent = {
            Button(
                modifier = Modifier.wrapContentSize(),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                onClick = changeDate,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text(date)
            }
        }
    )
}