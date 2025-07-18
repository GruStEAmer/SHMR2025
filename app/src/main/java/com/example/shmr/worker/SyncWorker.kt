package com.example.shmr.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.repository.AccountRepository

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val accountRepository: AccountRepository
    // private val transactionRepository: TransactionRepository,
    // private val categoryRepository: CategoryRepository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "SyncWorker"
        const val UNIQUE_PERIODIC_WORK_NAME = "PeriodicDataSync"
        const val ONE_TIME_WORK_TAG = "OneTimeDataSync"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "SyncWorker запущен.")
        var overallSuccess = true

        try {
            Log.d(TAG, "Начало синхронизации аккаунтов...")
            val unsyncedAccounts = accountRepository.getUnsyncedAccounts().getOrNull() ?: emptyList()

            if (unsyncedAccounts.isNotEmpty()) {
                Log.d(TAG, "Найдено ${unsyncedAccounts.size} несинхронизированных аккаунтов.")
                for (account in unsyncedAccounts) {
                    val syncResult = accountRepository.syncAccountToServer(account)
                    if (syncResult.isFailure) {
                        overallSuccess = false
                        Log.e(TAG, "Ошибка синхронизации аккаунта ID ${account.id}: ${syncResult.exceptionOrNull()?.message}")
                    } else {
                        Log.d(TAG, "Аккаунт ID ${account.id} успешно синхронизирован.")
                    }
                }
            } else {
                Log.d(TAG, "Несинхронизированных аккаунтов не найдено.")
            }

            // --- Синхронизация Транзакций (когда будет готово) ---
            /*
            Log.d(TAG, "Начало синхронизации транзакций...")
            val unsyncedTransactions = transactionRepository.getUnsyncedTransactions()
            if (unsyncedTransactions.isNotEmpty()) {
                Log.d(TAG, "Найдено ${unsyncedTransactions.size} несинхронизированных транзакций.")
                for (transaction in unsyncedTransactions) {
                    val syncResult = transactionRepository.syncTransactionToServer(transaction)
                    if (syncResult.isFailure) {
                        overallSuccess = false
                        Log.e(TAG, "Ошибка синхронизации транзакции ID ${transaction.id}: ${syncResult.exceptionOrNull()?.message}")
                    } else {
                        Log.d(TAG, "Транзакция ID ${transaction.id} успешно синхронизирована.")
                    }
                }
            } else {
                Log.d(TAG, "Несинхронизированных транзакций не найдено.")
            }
            */

            // --- Синхронизация Категорий (когда будет готово) ---
            /*
            Log.d(TAG, "Начало синхронизации категорий...")
            val unsyncedCategories = categoryRepository.getUnsyncedCategories()
            if (unsyncedCategories.isNotEmpty()) {
                Log.d(TAG, "Найдено ${unsyncedCategories.size} несинхронизированных категорий.")
                for (category in unsyncedCategories) {
                    val syncResult = categoryRepository.syncCategoryToServer(category)
                    if (syncResult.isFailure) {
                        overallSuccess = false
                        Log.e(TAG, "Ошибка синхронизации категории ID ${category.id}: ${syncResult.exceptionOrNull()?.message}")
                    } else {
                        Log.d(TAG, "Категория ID ${category.id} успешно синхронизирована.")
                    }
                }
            } else {
                Log.d(TAG, "Несинхронизированных категорий не найдено.")
            }
            */

            return if (overallSuccess) {
                Log.d(TAG, "Вся синхронизация данных успешно завершена.")
                Result.success()
            } else {
                Log.w(TAG, "Во время синхронизации произошли ошибки. Повтор попытки позже.")
                Result.retry()
            }

        } catch (e: Exception) {
            Log.e(TAG, "Критическая ошибка в SyncWorker: ${e.message}", e)
            return Result.retry()
        }
    }
}
