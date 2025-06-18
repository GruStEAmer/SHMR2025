package com.example.shmr.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shmr.R
import com.example.shmr.presentation.listItems.CategoryListItem
import com.example.shmr.presentation.viewModels.CategoryViewModel

@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory)) {

    val uiState = categoryViewModel.uiState
    var value by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TextField(
            value = value,
            onValueChange = { it-> value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            singleLine = true,
            label = { Text(stringResource(R.string.categories_search_category)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable{}
                )
            }
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = uiState.value,
                key = { it -> it.id }
            ) { it ->
                CategoryListItem(
                    name = it.name,
                    emoji = it.emoji
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}