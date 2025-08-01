package com.example.data.repositoryImpl

import com.example.data.local.dao.AccountDao
import com.example.data.local.entity.LocalAccount
import com.example.data.mapper.toLocalAccount
import com.example.data.network.apiService.AccountApiService
import com.example.data.network.model.account.AccountUpdateRequest
import com.example.data.repository.AccountRepository
import java.net.UnknownHostException
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApiService: AccountApiService,
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun getAccountById(id: Int): Result<LocalAccount> {
        return try {
            val localAccount = accountDao.getAccountById(id)
            if (localAccount != null) {
                Result.success(localAccount)
            } else {
                val refreshResult = refreshAccountById(id)
                if (refreshResult.isSuccess) {
                    val refreshedLocalAccount = accountDao.getAccountById(id)
                    if (refreshedLocalAccount != null) {
                        Result.success(refreshedLocalAccount)
                    } else {
                        Result.failure(Exception("Данные для счета $id не найдены даже после обновления."))
                    }
                } else {
                    Result.failure(refreshResult.exceptionOrNull() ?: Exception("Ошибка получения данных для счета $id."))
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка доступа к локальным данным счета $id: ${e.message}"))
        }
    }

    override suspend fun putAccountById(
        id: Int,
        accountUpdateRequest: AccountUpdateRequest
    ): Result<LocalAccount> {
        try {
            val localAccountToUpdate = LocalAccount(
                id = id,
                name = accountUpdateRequest.name,
                balance = accountUpdateRequest.balance,
                currency = accountUpdateRequest.currency,
                lastModified = System.currentTimeMillis(),
                isSynced = false
            )
            accountDao.insertAccount(localAccountToUpdate)
            // scheduleAccountSyncWorker() // WorkManager будет позже
            return Result.success(localAccountToUpdate)
        } catch (e: Exception) {
            return Result.failure(Exception("Ошибка при локальном сохранении счета: ${e.message}"))
        }
    }

    override suspend fun refreshAccountById(id: Int): Result<Unit> {
        return try {
            val response = accountApiService.getAccountById(id)
            if (response.isSuccessful) {
                val accountResponse = response.body()
                if (accountResponse != null) {
                    val localAccount = accountResponse.toLocalAccount().copy(isSynced = true)
                    accountDao.insertAccount(localAccount)
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Ответ сервера пуст при обновлении счета $id."))
                }
            } else {
                Result.failure(Exception("Сетевая ошибка ${response.code()} при обновлении счета $id"))
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к интернету при обновлении счета $id"))
        } catch (e: Exception) {
            Result.failure(Exception("Не удалось обновить счет $id: ${e.message}"))
        }
    }
    override suspend fun getUnsyncedAccounts(): Result<List<LocalAccount>> {
        return try {
            Result.success(accountDao.getUnsyncedAccounts())
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка получения несинхронизированных аккаунтов из БД: ${e.message}"))
        }
    }

    override suspend fun syncAccountToServer(account: LocalAccount): Result<Unit> {
        if (account.isSynced) {
            return Result.success(Unit)
        }

        try {
            val updateRequest = AccountUpdateRequest(
                name = account.name,
                balance = account.balance,
                currency = account.currency
            )
            val response = accountApiService.putAccountById(account.id, updateRequest)

            if (response.isSuccessful) {
                val syncedAccount = account.copy(isSynced = true, lastModified = System.currentTimeMillis())
                accountDao.insertAccount(syncedAccount) // insertOrReplace обновит запись
                return Result.success(Unit)
            } else {
                return Result.failure(
                    Exception("Ошибка сервера ${response.code()} при синхронизации счета ID ${account.id}: ${response.message()}")
                )
            }
        } catch (e: UnknownHostException) {
            return Result.failure(Exception("Нет подключения к интернету для синхронизации счета ID ${account.id}."))
        } catch (e: Exception) {
            return Result.failure(Exception("Ошибка при синхронизации счета ID ${account.id}: ${e.message}"))
        }
    }

}