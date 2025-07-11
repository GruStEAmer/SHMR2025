package com.example.account.di.module

import androidx.lifecycle.ViewModel
import com.example.account.di.annotations.ViewModelKey
import com.example.account.ui.screens.AccountViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel): ViewModel
}