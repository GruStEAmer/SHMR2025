package com.example.shmr.data.repository

import com.example.network.apiService.CategoryApiService
import com.example.shmr.domain.model.category.Category
import com.example.shmr.domain.repository.CategoryRepository
import java.net.UnknownHostException

class CategoryRepositoryImpl(
    private val categoryApiService: CategoryApiService
): CategoryRepository
{
    override suspend fun getCategories(): Result<List<Category>> {
        return try{
            val response = categoryApiService.getCategories()

            when(response.code()) {
                200 -> Result.success(response.body()!!)
                else -> {
                    Result.failure(Exception("${response.code()}"))
                }
            }
        } catch(e : UnknownHostException){
            Result.failure(Exception("Нет подключения к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("$e"))
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<Category>> {
        return try{
            val response = categoryApiService.getCategoriesByType(isIncome)

            when(response.code()) {
                200 -> Result.success(response.body()!!)
                else -> {
                    Result.failure(Exception("${response.code()}"))
                }
            }
        } catch(e : UnknownHostException){
            Result.failure(Exception("Нет подключения к интернету"))
        } catch (e: Exception){
            Result.failure(Exception("$e"))
        }
    }
}