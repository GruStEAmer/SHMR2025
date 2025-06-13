package com.example.shmr.ui.listItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionListItem(
    name: String,
    emoji: String?,
    categoryId: Int,
    amount: Int,
    comment: String?,
    currency: String
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = name,
                    fontSize = 16.sp
                )
                Text(
                    text = "$amount $cur",
                    fontSize = 16.sp
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable{ },

        supportingContent = {
            comment?.let{
                Text(
                    text = comment,
                    fontSize = 12.sp
                )
            }
        },
        leadingContent = emoji?.let{
             {
                Text(
                    text = emoji,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFD4FAE6)),
                    fontSize = 22.sp
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    )
    HorizontalDivider()
}