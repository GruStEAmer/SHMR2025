package com.example.shmr.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shmr.listSettings
import com.example.shmr.ui.listItems.SettingListItem

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        ChangeThemeItem()
        HorizontalDivider()
        listSettings.forEach {
            SettingListItem(it)
        }
    }
}

@Composable
fun ChangeThemeItem() {
    var switchState by rememberSaveable { mutableStateOf(false)}
    ListItem(
        headlineContent = {
            Text(
                text = "Темная тема"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { switchState = !switchState },
        trailingContent = {
            Switch(
                checked = switchState,
                onCheckedChange = { switchState = !switchState },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF2AE881),
                    checkedTrackColor = Color(0xFFD4FAE6),
                    checkedBorderColor = Color(0xFF2AE881)
                )
            )
        }
    )
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingsScreen()
}