package com.example.shmr.data.repository

import com.example.shmr.data.remote.api.CategoryApiService
import com.example.shmr.domain.model.category.Category
import com.example.shmr.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryApiService: CategoryApiService
): CategoryRepository
{
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val response = categoryApiService.getCategories()

            when {
                response.code() == 200 -> Result.success(response.body()!!)
                response.code() == 401 -> Result.failure(Exception("Неавторизированный доступ"))
                else -> Result.failure(Exception("Ошибка сервера: ${response.code()}"))
            }
        } catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<Category>> {
        return try {
            val response = categoryApiService.getCategoriesByType(isIncome)

            when {
                response.code() == 200 -> Result.success(response.body()!!)
                response.code() == 400 -> Result.failure(Exception("Некорректный параметр"))
                response.code() == 401 -> Result.failure(Exception("Неавторизированный доступ"))
                else -> Result.failure(Exception("Ошибка сервера: ${response.code()}"))
            }

        } catch(e: Exception){
            Result.failure(e)
        }
    }
}