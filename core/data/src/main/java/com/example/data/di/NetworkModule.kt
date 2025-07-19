package com.example.data.di

import com.example.data.network.apiService.AccountApiService
import com.example.data.network.apiService.CategoryApiService
import com.example.data.network.apiService.TransactionApiService
import com.example.data.network.utils.ApiKeyInterceptor
import com.example.data.network.utils.RetryInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(RetryInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @[Provides Singleton]
    internal fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
        return retrofit.create<CategoryApiService>()
    }
    @[Provides Singleton]
    internal fun provideAccountApiService(retrofit: Retrofit): AccountApiService {
        return retrofit.create<AccountApiService>()
    }
    @[Provides Singleton]
    internal fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService {
        return retrofit.create<TransactionApiService>()
    }
}