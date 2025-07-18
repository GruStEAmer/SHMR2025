package com.example.shmr.worker

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.network.model.transaction.Transaction
import com.example.data.repository.AccountRepository
import com.example.data.repository.CategoryRepository
import com.example.data.repository.TransactionRepository

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
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
            try {
                Log.d(TAG, "Начало синхронизации транзакций...")
                val unsyncedTransactions = transactionRepository.getUnsyncedTransactions()

                if (unsyncedTransactions.isNotEmpty()) {
                    Log.d(TAG, "Найдено ${unsyncedTransactions.size} несинхронизированных транзакций.")
                    for (transaction in unsyncedTransactions) {
                        Log.d(TAG, "Синхронизация транзакции с локальным ID ${transaction.id} (Сумма: ${transaction.amount}, Дата: ${transaction.transactionDate})...")
                        val syncAttemptResult = transactionRepository.syncLocalTransactionToServer(transaction)

                        syncAttemptResult.fold(
                            onSuccess = { serverResponse ->
                                val finalizeResult = if (transaction.id < 0) {
                                    val serverTransactionModel = Transaction(
                                        id = serverResponse.id,
                                        accountId = serverResponse.account.id,
                                        categoryId = serverResponse.category.id,
                                        amount = serverResponse.amount,
                                        transactionDate = serverResponse.transactionDate,
                                        comment = serverResponse.comment,
                                        createdAt = serverResponse.createdAt,
                                        updatedAt = serverResponse.updatedAt
                                    )
                                    transactionRepository.finalizeTransactionSync(transaction.id, serverTransactionModel)
                                } else {
                                    transactionRepository.finalizeTransactionUpdate(transaction.id, serverResponse)
                                }

                                if (finalizeResult.isSuccess) {
                                    Log.d(TAG, "Транзакция ID ${transaction.id} (старый) -> ${serverResponse.id} (новый/обновленный) успешно синхронизирована и финализирована.")
                                } else {
                                    overallSuccess = false
                                    Log.e(TAG, "Ошибка финализации синхронизации для транзакции ID ${transaction.id}: ${finalizeResult.exceptionOrNull()?.message}")
                                }
                            },
                            onFailure = { error ->
                                overallSuccess = false
                                Log.e(TAG, "Ошибка синхронизации (отправки) транзакции ID ${transaction.id}: ${error.message}")
                            }
                        )
                    }
                } else {
                    Log.d(TAG, "Несинхронизированных транзакций не найдено.")
                }
            } catch (e: Exception) {
                overallSuccess = false
                Log.e(TAG, "Критическая ошибка при синхронизации транзакций: ${e.message}", e)
            }


            Log.d(TAG, "Начало синхронизации категорий...")
            val unsyncedCategories = categoryRepository.refreshAllCategoriesFromNetwork()

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
