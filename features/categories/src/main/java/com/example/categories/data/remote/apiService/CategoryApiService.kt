package com.example.categories.data.remote.apiService

import com.example.categories.data.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): Response<List<Category>>
}