package com.example.categories.di.module

import androidx.lifecycle.ViewModel
import com.example.categories.di.annotations.ViewModelKey
import com.example.categories.ui.screens.CategoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel
}