package com.example.categories.di.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.categories.di.component.CategoriesComponent
import com.example.categories.di.deps.CategoryComponentViewModel

@Composable
fun rememberCategoriesComponent(): CategoriesComponent {
    val componentViewModel: CategoryComponentViewModel = viewModel()
    return remember { componentViewModel.categoriesComponent }
}