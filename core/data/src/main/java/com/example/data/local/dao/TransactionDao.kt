package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import com.example.data.local.entity.LocalTransaction
import com.example.data.local.entity.TransactionWithRelations

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceTransaction(transaction: LocalTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceTransactionsList(transactionsList: List<LocalTransaction>)

    @Update
    suspend fun updateTransaction(transaction: LocalTransaction)

    @Query("SELECT * FROM transactions " +
            "WHERE accountId = :accountId AND transactionDate >= :startDate AND transactionDate <= :endDate " +
            "ORDER BY transactionDate DESC, createdAt DESC")
    suspend fun getTransactionsForAccountByPeriod(accountId: Int, startDate: String, endDate: String): List<TransactionWithRelations>

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    suspend fun getTransactionWithRelationsById(transactionId: Int): TransactionWithRelations?

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: Int): LocalTransaction?

    @Query("SELECT * FROM transactions WHERE isSynced = 0") // 0 для false в SQLite
    suspend fun getUnsyncedTransactions(): List<LocalTransaction>

    @Query("UPDATE transactions SET isSynced = :isSynced, updatedAt = :updatedAt WHERE id = :transactionId")
    suspend fun updateTransactionSyncStatus(transactionId: Int, isSynced: Boolean, updatedAt: Long)

    @Query("UPDATE transactions SET id = :newTransactionId, isSynced = 1, updatedAt = :serverUpdatedAt WHERE id = :oldTransactionId")
    suspend fun updateLocalTransactionIdAfterSync(oldTransactionId: Int, newTransactionId: Int, serverUpdatedAt: Long)


    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun deleteTransactionByIdPhysically(transactionId: Int)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()

    @Transaction
    suspend fun replaceAllTransactionsForAccount(accountId: Int, transactions: List<LocalTransaction>) {
        deleteTransactionsByAccountId(accountId)
        if (transactions.isNotEmpty()) {
            insertOrReplaceTransactionsList(transactions)
        }
    }

    @Query("DELETE FROM transactions WHERE accountId = :accountId")
    suspend fun deleteTransactionsByAccountId(accountId: Int)

}

