package com.example.shmr.ui.listItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingListItem(
    name: String
){
    ListItem(
        headlineContent = {
            Text(
                text = name,
                fontSize = 16.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable{ },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    )
    HorizontalDivider()
}