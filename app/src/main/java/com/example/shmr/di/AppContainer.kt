package com.example.shmr.di

import com.example.shmr.BuildConfig
import com.example.shmr.data.remote.api.AccountApiService
import com.example.shmr.data.remote.api.CategoryApiService
import com.example.shmr.data.remote.api.TransactionApiService
import com.example.shmr.data.repository.AccountRepositoryImpl
import com.example.shmr.data.repository.CategoryRepositoryImpl
import com.example.shmr.data.repository.TransactionRepositoryImpl
import com.example.shmr.domain.repository.AccountRepository
import com.example.shmr.domain.repository.CategoryRepository
import com.example.shmr.domain.repository.TransactionRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val categoryRepository: CategoryRepository
    val accountRepository: AccountRepository
    val transactionRepository: TransactionRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://shmr-finance.ru/api/v1/"

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .apply {
                    header("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
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

    //Repository Category
    private val categoryRetrofitService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }
    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(categoryRetrofitService)
    }

    //Repository Account
    private val accountRetrofitService: AccountApiService by lazy {
        retrofit.create(AccountApiService::class.java)
    }
    override val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(accountRetrofitService)
    }

    //Repository Transaction
    private val transactionRetrofitService: TransactionApiService by lazy{
        retrofit.create(TransactionApiService::class.java)
    }
    override val transactionRepository: TransactionRepository by lazy {
        TransactionRepositoryImpl(transactionRetrofitService)
    }
}