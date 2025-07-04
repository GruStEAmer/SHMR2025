package com.example.shmr.features.categories.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.shmr.core.ui.components.ErrorScreen
import com.example.shmr.core.ui.components.LoadingScreen
import com.example.shmr.core.ui.components.listItems.CategoryListItem
import com.example.shmr.core.ui.navigationBar.AppTopBar
import com.example.shmr.core.ui.state.UiState
import com.example.shmr.domain.model.category.Category

@Composable
fun CategoryScreen() {
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory)
    val uiState by categoryViewModel.categoryUiState.collectAsState()
    val targetCategories by categoryViewModel.targetCategoryUiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> CategoryScreenUi(
            categories = targetCategories,
            searchCategories = { query -> categoryViewModel.searchCategory(query) }
        )
        is UiState.Error -> ErrorScreen(
            message = (uiState as UiState.Error).error.message,
            reloadData = { categoryViewModel.getCategories() }
        )
    }
}

@Composable
fun CategoryScreenUi(
    categories: List<Category>,
    searchCategories: (String) -> Unit
) {
    var searchValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Мои статьи",
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            TextField(
                value = searchValue,
                onValueChange = { searchValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                singleLine = true,
                label = { Text(stringResource(R.string.categories_search_category)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.clickable {
                            searchCategories(searchValue)
                        }
                    )
                }
            )
            HorizontalDivider()
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = categories,
                    key = { it.id }
                ) { category ->
                    CategoryListItem(
                        name = category.name,
                        emoji = category.emoji
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}