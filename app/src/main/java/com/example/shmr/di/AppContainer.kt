package com.example.shmr.di

import com.example.shmr.data.remote.api.CategoryApiService
import com.example.shmr.data.repository.CategoryRepositoryImpl
import com.example.shmr.domain.repository.CategoryRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val categoryRepository: CategoryRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://shmr-finance.ru/api/v1/"

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .apply {
                    header("Authorization", "Bearer FDOxO0k6LDmc1SRQJ3Knv5GD")
                }
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val categoryRetrofitService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(categoryRetrofitService)
    }
}