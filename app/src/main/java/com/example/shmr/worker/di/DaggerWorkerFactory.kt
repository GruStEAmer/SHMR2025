package com.example.shmr.worker.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.data.repository.AccountRepository
import com.example.data.repository.CategoryRepository
import com.example.data.repository.TransactionRepository
import com.example.shmr.worker.SyncWorker
import javax.inject.Inject

class DaggerWorkerFactory @Inject constructor(
    private val accountRepository: AccountRepository,
    //private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(
                    appContext,
                    workerParameters,
                    accountRepository,
                    // transactionRepository,
                    categoryRepository
                )
            else -> null
        }
    }
}
