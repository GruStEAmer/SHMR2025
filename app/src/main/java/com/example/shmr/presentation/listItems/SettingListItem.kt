package com.example.shmr.presentation.listItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shmr.R

@Composable
fun SettingListItem(
    textId: Int
){
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(textId),
                fontSize = 16.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable{ },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_right),
                contentDescription = ""
            )
        }
    )
    HorizontalDivider()
}