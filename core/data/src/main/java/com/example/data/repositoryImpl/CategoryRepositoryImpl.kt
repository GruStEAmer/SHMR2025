package com.example.data.repositoryImpl

import com.example.data.network.apiService.CategoryApiService
import com.example.data.network.model.category.Category
import com.example.data.repository.CategoryRepository
import java.net.UnknownHostException
import javax.inject.Inject

internal class CategoryRepositoryImpl @Inject constructor(
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
                200 -> Result.success(response.body()!!.map{it})
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