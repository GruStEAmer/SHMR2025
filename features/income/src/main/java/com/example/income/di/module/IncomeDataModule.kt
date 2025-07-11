package com.example.income.di.module

import com.example.income.data.remote.apiService.IncomeApiService
import com.example.income.data.repository.IncomeRepositoryImpl
import com.example.income.di.annotations.IncomeScope
import com.example.income.domain.repository.IncomeRepository
import com.example.income.ui.screens.IncomeViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
internal class IncomeDataModule {

    @[Provides IncomeScope]
    fun provideIncomeApiService(retrofit: Retrofit): IncomeApiService {
        return retrofit.create<IncomeApiService>()
    }

    @[Provides IncomeScope]
    fun provideIncomeRepository(incomeApiService: IncomeApiService): IncomeRepository =
        IncomeRepositoryImpl(incomeApiService = incomeApiService)

    @[Provides IncomeScope]
    fun provideIncomeViewModel(incomeRepository: IncomeRepository) =
        IncomeViewModel(repository = incomeRepository)
}