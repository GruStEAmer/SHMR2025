package com.example.shmr.data.repository

import android.util.Log
import com.example.shmr.data.remote.api.CategoryApiService
import com.example.shmr.domain.model.category.Category
import com.example.shmr.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryApiService: CategoryApiService
): CategoryRepository
{
    override suspend fun getCategories(): List<Category> {
        val response = categoryApiService.getCategories()
        return try{
            when {
                response.code() == 200 -> response.body()!!
                else -> {
                    Log.e("CategoryNetworkError", "Error: ${response.code()}")
                    listOf<Category>()
                }
            }
        } catch(e: Exception){
            Log.e("CategoryError", "Error: $e")
            listOf<Category>()
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        val response = categoryApiService.getCategoriesByType(isIncome)
        return try{
            when {
                response.code() == 200 -> response.body()!!
                else -> {
                    Log.e("CategoryNetworkError", "Error: ${response.code()}")
                    listOf<Category>()
                }
            }
        } catch(e: Exception){
            Log.e("CategoryError", "Error: $e")
            listOf<Category>()
        }
    }
}