package com.example.shmr.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shmr.StartAccount
import com.example.shmr.core.ui.theme.LightGreen
import java.time.LocalDate

@Composable
fun StartEndSumDetail(
    startDate: LocalDate,
    endDate: LocalDate,
    changeStartDatePicker: () -> Unit,
    changeEndDatePicker: () -> Unit,
    balance: String,
) {

    val cur = when(StartAccount.CURRENCY){
        "₽" -> "₽"
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> "-"
    }

    Column(
        modifier = Modifier
    ) {
        ListItem(
            headlineContent = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text(
                        text = "Начало",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$startDate",
                        fontSize = 16.sp
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(
                    onClick = changeStartDatePicker
                ),
            colors = ListItemDefaults.colors(
                LightGreen,
            )
        )
        HorizontalDivider()
        ListItem(
            headlineContent = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ){
                    Text(
                        text = "Конец",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$endDate",
                        fontSize = 16.sp
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(
                    onClick = changeEndDatePicker
                ),
            colors = ListItemDefaults.colors(
                LightGreen,
            )
        )
        HorizontalDivider()
        ListItem(
            headlineContent = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Сумма",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$balance $cur"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ListItemDefaults.colors(
                LightGreen,
            )
        )

    }
}