package com.example.shmr.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shmr.R

@Composable
fun CircleButton(modifier:Modifier = Modifier) {
    Icon(
        painter = painterResource(R.drawable.ic_button_circle),
        contentDescription = "",
        modifier = modifier
            .padding(12.dp)
            .clip(shape = CircleShape)
            .clickable{}
        ,
        tint = Color.Unspecified
    )
}

@Preview
@Composable
fun CircleButtonPreview() {
    CircleButton()
}