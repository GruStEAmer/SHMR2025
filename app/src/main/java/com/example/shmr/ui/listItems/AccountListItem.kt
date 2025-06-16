package com.example.shmr.ui.listItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shmr.R
import com.example.shmr.ui.theme.LightGreen

@Composable
fun AccountListItem(
    name: String,
    balance: String = "",
    currency: String,
    emoji: String? = null
) {
    val cur = when(currency) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> "-"
    }
    ListItem(
        headlineContent = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp
                )
                Text(
                    text = "$balance $cur",
                    fontSize = 16.sp
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable{ },
        leadingContent = emoji?.let{
            {
                Text(
                    text = emoji,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                    ,
                    fontSize = 22.sp
                )
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_right),
                contentDescription = ""
            )
        },
        colors = ListItemDefaults.colors(
            LightGreen,
        )
    )
}