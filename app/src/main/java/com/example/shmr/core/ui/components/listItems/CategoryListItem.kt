package com.example.shmr.core.ui.components.listItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shmr.core.ui.theme.LightGreen

@Composable
fun CategoryListItem(
    name: String,
    emoji: String
) {
    ListItem(
        headlineContent = {
            Text(
                text = name,
                fontSize = 16.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),

        leadingContent = {
            Text(
                text = emoji,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LightGreen)
                ,
                fontSize = 22.sp
            )
        }
    )
    HorizontalDivider()
}
