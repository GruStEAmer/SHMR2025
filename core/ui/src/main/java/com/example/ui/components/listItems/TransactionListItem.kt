package com.example.ui.components.listItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R
import com.example.ui.theme.LightGreen


@Composable
fun TransactionListItem(
    categoryId: Int,
    categoryName: String,
    emoji: String?,
    amount: String,
    currency: String = "RUB",
    comment: String? = null,
    date: String? = null
) {
    val cur = when(currency) {
        "₽" -> "₽"
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> "-"
    }
    ListItem(
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = categoryName,
                    fontSize = 16.sp
                )
                Column(
                    horizontalAlignment = Alignment.End
                ){
                    Text(
                        text = "$amount $cur",
                        fontSize = 16.sp
                    )
                    date?.let{
                        Text(
                            text = "${date.subSequence(11,16)}",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { },

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
                        .background(LightGreen),
                    fontSize = 22.sp
                )
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_right),
                contentDescription = ""
            )
        }
    )
    HorizontalDivider()
}