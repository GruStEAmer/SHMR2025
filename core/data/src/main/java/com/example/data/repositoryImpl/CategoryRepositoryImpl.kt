package com.example.data.repositoryImpl

import android.util.Log
import com.example.data.local.dao.CategoryDao
import com.example.data.local.entity.LocalCategory
import com.example.data.mapper.toLocalCategory
import com.example.data.network.apiService.CategoryApiService
import com.example.data.repository.CategoryRepository
import java.net.UnknownHostException
import javax.inject.Inject
import com.example.data.network.model.category.Category as NetworkCategory

internal class CategoryRepositoryImpl @Inject constructor(
    private val categoryApiService: CategoryApiService,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    companion object {
        private const val TAG = "CategoryRepoImpl"
    }

    override suspend fun getCategories(): Result<List<LocalCategory>> {
        try {
            val localCategories = categoryDao.getAllCategories()
            if (localCategories.isNotEmpty()) {
                Log.d(TAG, "getCategories: Loaded ${localCategories.size} categories from local DB.")
                return Result.success(localCategories)
            } else {
                Log.d(TAG, "getCategories: Local DB is empty. Fetching from network...")
                val refreshResult = refreshAllCategoriesFromNetwork()
                return if (refreshResult.isSuccess) {
                    val newLocalCategories = categoryDao.getAllCategories()
                    Log.d(TAG, "getCategories: Loaded ${newLocalCategories.size} categories from DB after network refresh.")
                    Result.success(newLocalCategories)
                } else {
                    Log.w(TAG, "getCategories: Failed to refresh categories from network.")
                    refreshResult.map { emptyList() }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCategories: Error accessing local category data: ${e.message}", e)
            return Result.failure(Exception("Ошибка доступа к локальным данным категорий: ${e.message}", e))
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<LocalCategory>> {
        try {
            val localCategories = categoryDao.getCategoriesByType(isIncome)
            if (localCategories.isNotEmpty()) {
                Log.d(TAG, "getCategoriesByType($isIncome): Loaded ${localCategories.size} categories from local DB.")
                return Result.success(localCategories)
            } else {
                Log.d(TAG, "getCategoriesByType($isIncome): Local DB empty for type. Fetching from network...")
                val refreshResult = refreshAllCategoriesFromNetwork()
                return if (refreshResult.isSuccess) {
                    val newLocalCategories = categoryDao.getCategoriesByType(isIncome)
                    Log.d(TAG, "getCategoriesByType($isIncome): Loaded ${newLocalCategories.size} categories from DB after network refresh.")
                    Result.success(newLocalCategories)
                } else {
                    Log.w(TAG, "getCategoriesByType($isIncome): Failed to refresh categories of type $isIncome from network.")
                    refreshResult.map { emptyList() }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCategoriesByType($isIncome): Error accessing local category data: ${e.message}", e)
            return Result.failure(Exception("Ошибка доступа к локальным данным категорий (тип: $isIncome): ${e.message}", e))
        }
    }
    override suspend fun refreshAllCategoriesFromNetwork(): Result<Unit> {
        return try {
            val response = categoryApiService.getCategories()
            if (response.isSuccessful) {
                val networkCategories = response.body()
                if (networkCategories != null) {
                    categoryDao.replaceAllCategories(networkCategories.map { it.toLocalCategory() })
                    Result.success(Unit)
                } else {
                    categoryDao.deleteAllCategories()
                    Result.success(Unit)
                }
            } else {
                Log.e(TAG, "Network error ${response.code()} - ${response.message()}.")
                Result.failure(Exception("Сетевая ошибка ${response.code()} при обновлении всех категорий: ${response.message()}"))
            }
        } catch (e: UnknownHostException) {
            Log.w(TAG, "No internet connection.", e)
            Result.failure(Exception("Нет подключения к интернету при обновлении всех категорий.", e))
        } catch (e: Exception) {
            Result.failure(Exception("Не удалось обновить все категории: ${e.message}", e))
        }
    }
}