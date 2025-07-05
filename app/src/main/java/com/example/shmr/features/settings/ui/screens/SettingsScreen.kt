package com.example.shmr.features.settings.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shmr.R
import com.example.shmr.core.ui.components.listItems.SettingListItem
import com.example.shmr.core.ui.navigationBar.AppTopBar
import com.example.shmr.core.ui.theme.Green
import com.example.shmr.core.ui.theme.LightGreen

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Настройки",
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            ChangeThemeItem()
            HorizontalDivider()
            listSettings.forEach {
                SettingListItem(it)
            }
        }
    }
}

@Composable
fun ChangeThemeItem() {
    var switchState by rememberSaveable { mutableStateOf(false)}
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(R.string.settings_dark_theme_switch)
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
                    checkedThumbColor = Green,
                    checkedTrackColor = LightGreen,
                    checkedBorderColor = Green
                )
            )
        }
    )
}
val listSettings = listOf(
    R.string.settings_basic_color,
    R.string.settings_sound,
    R.string.settings_haptics,
    R.string.settings_code_password,
    R.string.settings_synchronization,
    R.string.settings_language,
    R.string.settings_about_program,
)

@Preview
@Composable
fun SettingScreenPreview() {
    SettingsScreen()
}