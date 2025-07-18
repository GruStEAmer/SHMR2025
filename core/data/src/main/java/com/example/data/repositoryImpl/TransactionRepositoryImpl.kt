package com.example.data.repositoryImpl

import android.util.Log
import com.example.data.local.dao.AccountDao
import com.example.data.local.dao.CategoryDao
import com.example.data.local.dao.TransactionsDao
import com.example.data.local.entity.LocalAccount
import com.example.data.local.entity.LocalCategory
import com.example.data.local.entity.LocalTransaction
import com.example.data.mapper.toAccountBrief
import com.example.data.mapper.toAccountBriefNetwork
import com.example.data.mapper.toCategory
import com.example.data.mapper.toCategoryNetwork
import com.example.data.mapper.toLocalTransaction
import com.example.data.mapper.toLocalTransactionFromPost
import com.example.data.mapper.toTransactionRequest
import com.example.data.mapper.toTransactionResponse
import com.example.data.network.apiService.TransactionApiService
import com.example.data.network.model.account.AccountBrief
import com.example.data.network.model.category.Category
import com.example.data.network.model.transaction.Transaction
import com.example.data.network.model.transaction.TransactionRequest
import com.example.data.network.model.transaction.TransactionResponse
import com.example.data.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionApiService: TransactionApiService,
    private val transactionsDao: TransactionsDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao
) : TransactionRepository {

    companion object {
        private const val TAG = "TransactionRepoImpl"
        fun generateTemporaryId(): Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt() * -1
    }

    override suspend fun postTransaction(transactionRequest: TransactionRequest): Result<Transaction> {
        val temporaryId = generateTemporaryId()
        val currentTime = System.currentTimeMillis()
        val localTransaction = LocalTransaction(
            id = temporaryId,
            accountId = transactionRequest.accountId,
            categoryId = transactionRequest.categoryId,
            amount = transactionRequest.amount.toDoubleOrNull() ?: 0.0,
            transactionDate = transactionRequest.transactionDate,
            comment = transactionRequest.comment,
            isSynced = false,
            createdAt = currentTime,
            updatedAt = currentTime
        )
        transactionsDao.insertOrReplaceTransaction(localTransaction)
        Log.d(TAG, "postTransaction: Saved local transaction with tempId: $temporaryId")

        return try {
            val response = transactionApiService.postTransaction(transactionRequest)
            if (response.isSuccessful && response.body() != null) {
                val serverTransaction = response.body()!!
                finalizeTransactionSync(temporaryId, serverTransaction)
                Result.success(serverTransaction)
            } else {
                Result.failure(Exception("Серверная ошибка ${response.code()}: ${response.message()}. Транзакция сохранена локально (ID: $temporaryId)."))
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к интернету. Транзакция сохранена локально (ID: $temporaryId)."))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка при создании транзакции (ID: $temporaryId): ${e.localizedMessage}. Сохранена локально."))
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionResponse> {
        try {
            val localFullTransaction = transactionsDao.getTransactionWithRelationsById(id)
            if (localFullTransaction != null) {
                Log.d(TAG, "getTransactionById:${localFullTransaction.transaction.isSynced}")
                val localAccount = accountDao.getAccountById(localFullTransaction.transaction.accountId)?.toAccountBrief()
                val localCategory = categoryDao.getCategoryById(localFullTransaction.transaction.categoryId)?.toCategory()

                if (localAccount != null && localCategory != null) {
                    return Result.success(
                        localFullTransaction.transaction.toTransactionResponse(localAccount, localCategory)
                    )
                } else {
                    Log.w(TAG, "getTransactionById: Could not find related account/category for local transaction $id.")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getTransactionById: Error accessing local DB for transaction $id: ${e.message}", e)
        }

        if (id > 0) {
            return try {
                val response = transactionApiService.getTransactionById(id)
                if (response.isSuccessful && response.body() != null) {
                    val serverTransactionResponse = response.body()!!
                    transactionsDao.insertOrReplaceTransaction(serverTransactionResponse.toLocalTransaction(isSynced = true))
                    Log.d(TAG, "getTransactionById: Fetched and saved transaction $id from network.")
                    Result.success(serverTransactionResponse)
                } else {
                    Log.w(TAG, "getTransactionById: Network error for $id. Code: ${response.code()} - ${response.message()}")
                    Result.failure(Exception("Сетевая ошибка ${response.code()} для транзакции $id: ${response.message()}"))
                }
            } catch (e: UnknownHostException) {
                Log.w(TAG, "getTransactionById: No internet for $id.", e)
                Result.failure(Exception("Нет подключения к интернету для загрузки транзакции $id."))
            } catch (e: Exception) {
                Log.e(TAG, "getTransactionById: Exception fetching $id from network: ${e.localizedMessage}", e)
                Result.failure(Exception("Ошибка при загрузке транзакции $id из сети: ${e.localizedMessage}"))
            }
        } else {
            Log.e(TAG, "getTransactionById: Negative ID $id transaction not found locally.")
            return Result.failure(Exception("Локальная транзакция с временным ID $id не найдена."))
        }
    }


    override suspend fun putTransactionById(id: Int, transactionRequest: TransactionRequest): Result<TransactionResponse> {
        val localTransactionToUpdate = transactionsDao.getTransactionById(id)
        if (localTransactionToUpdate == null) {
            Log.e(TAG, "putTransactionById: Transaction with id $id not found locally.")
            return Result.failure(Exception("Транзакция с ID $id не найдена локально для обновления."))
        }

        val updatedLocalTransaction = localTransactionToUpdate.copy(
            accountId = transactionRequest.accountId,
            categoryId = transactionRequest.categoryId,
            amount = transactionRequest.amount.toDoubleOrNull() ?: localTransactionToUpdate.amount,
            transactionDate = transactionRequest.transactionDate,
            comment = transactionRequest.comment,
            isSynced = false,
            updatedAt = System.currentTimeMillis()
        )
        transactionsDao.updateTransaction(updatedLocalTransaction)
        Log.d(TAG, "putTransactionById: Updated local transaction $id, marked as unsynced.")

        return try {
            val response = transactionApiService.putTransactionById(id, transactionRequest)
            if (response.isSuccessful && response.body() != null) {
                val serverTransaction = response.body()!!
                finalizeTransactionUpdate(id, serverTransaction)
                Result.success(serverTransaction)
            } else {
                Result.failure(Exception("Серверная ошибка ${response.code()}: ${response.message()}. Изменения сохранены локально (ID: $id)."))
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к интернету. Изменения сохранены локально (ID: $id)."))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка при обновлении транзакции (ID: $id): ${e.localizedMessage}. Изменения сохранены локально."))
        }
    }


    override suspend fun getTransactionsByAccountIdWithDate(
        accountId: Int,
        startDate: String,
        endDate: String
    ): Result<List<TransactionResponse>> {
        try {
            val localRelations = transactionsDao.getTransactionsForAccountByPeriod(accountId, startDate, endDate)
            if (localRelations.isNotEmpty()) {
                Log.d(TAG, "getTransactionsByAcc: Found ${localRelations.size} for account $accountId locally.")
                val responseList = mutableListOf<TransactionResponse>()
                for (relation in localRelations) {
                    val acc = accountDao.getAccountById(relation.transaction.accountId)?.toAccountBrief()
                    val cat = categoryDao.getCategoryById(relation.transaction.categoryId)?.toCategory()
                    if (acc != null && cat != null) {
                        responseList.add(relation.transaction.toTransactionResponse(acc, cat))
                    } else {
                        Log.w(TAG, "getTransactionsByAcc: Missing account/category for local transaction ${relation.transaction.id}")
                    }
                }
                if (responseList.isNotEmpty() || localRelations.isEmpty()) {
                    return Result.success(responseList)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getTransactionsByAcc: Error accessing local DB for account $accountId: ${e.message}", e)
        }

        Log.d(TAG, "getTransactionsByAcc: No local transactions for account $accountId or error/missing related. Fetching from network...")
        return try {
            val response = transactionApiService.getTransactionsByAccountIdWithDate(accountId, startDate, endDate)
            if (response.isSuccessful && response.body() != null) {
                val serverResponses = response.body()!!
                if (serverResponses.isNotEmpty()) {
                    val localToSave = serverResponses.map { it.toLocalTransaction(isSynced = true) }
                    transactionsDao.insertOrReplaceTransactionsList(localToSave)
                } else {
                    Log.d(TAG, "getTransactionsByAcc: Fetched 0 transactions for account $accountId from network.")
                }
                Result.success(serverResponses)
            } else {
                Result.failure(Exception("Сетевая ошибка ${response.code()} для счета $accountId: ${response.message()}"))
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к интернету для загрузки транзакций счета $accountId."))
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка при загрузке транзакций для счета $accountId из сети: ${e.localizedMessage}"))
        }
    }

    override suspend fun getUnsyncedTransactions(): List<LocalTransaction> {
        return withContext(Dispatchers.IO) {
            transactionsDao.getUnsyncedTransactions()
        }
    }

    override suspend fun syncLocalTransactionToServer(localTransaction: LocalTransaction): Result<TransactionResponse> {
        Log.d(TAG, "syncLocalTransactionToServer: Attempting to sync local transaction ID: ${localTransaction.id}")
        val request = localTransaction.toTransactionRequest()

        return if (localTransaction.id < 0) {
            try {
                val response = transactionApiService.postTransaction(request)
                if (response.isSuccessful && response.body() != null) {
                    val serverTransaction = response.body()!! // Это тип Transaction
                    Log.d(TAG, "syncLocalTransactionToServer (POST): Success for tempId ${localTransaction.id}. ServerId ${serverTransaction.id}")

                    val account = accountDao.getAccountById(serverTransaction.accountId)
                    val category = categoryDao.getCategoryById(serverTransaction.categoryId)

                    if (account != null && category != null) {

                        val transactionResponse = TransactionResponse(
                            id = serverTransaction.id,
                            account = account.toAccountBriefNetwork(),
                            category = category.toCategoryNetwork(),
                            amount = serverTransaction.amount,
                            transactionDate = serverTransaction.transactionDate,
                            comment = serverTransaction.comment,
                            createdAt = serverTransaction.createdAt,
                            updatedAt = serverTransaction.updatedAt
                        )
                        Result.success(transactionResponse)
                    } else {
                        Result.failure(Exception("Could not retrieve account/category details for new transaction after sync."))
                    }
                } else {
                    Result.failure(Exception("Server error ${response.code()} for POST"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            try {
                val response = transactionApiService.putTransactionById(localTransaction.id, request) // Возвращает Response<TransactionResponse>
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Server error ${response.code()} for PUT"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "syncLocalTransactionToServer (PUT): Exception for serverId ${localTransaction.id}", e)
                Result.failure(e)
            }
        }
    }

    override suspend fun finalizeTransactionSync(temporaryId: Int, serverResponse: Transaction): Result<Unit> {
        return try {
            val localToUpdate = serverResponse.toLocalTransaction(isSynced = true)

            transactionsDao.updateLocalTransactionIdAfterSync(
                oldTransactionId = temporaryId,
                newTransactionId = localToUpdate.id,
                serverUpdatedAt = localToUpdate.updatedAt
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "finalizeTransactionSync: Error finalizing sync for tempId $temporaryId: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override suspend fun finalizeTransactionUpdate(serverId: Int, serverResponse: TransactionResponse): Result<Unit> {
        return try {
            val localToUpdate = serverResponse.toLocalTransaction(isSynced = true)
            if (localToUpdate.id != serverId) {
                Log.w(TAG, "finalizeTransactionUpdate: Server ID mismatch! Expected $serverId, got ${localToUpdate.id}. Updating with server's ID.")
            }
            transactionsDao.insertOrReplaceTransaction(localToUpdate)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "finalizeTransactionUpdate: Error finalizing update for serverId $serverId: ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }
}

