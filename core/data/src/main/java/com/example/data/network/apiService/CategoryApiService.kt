package com.example.data.network.apiService

import com.example.data.network.model.category.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): Response<List<Category>>
}