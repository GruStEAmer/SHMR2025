package com.example.data.mapper

import com.example.data.local.entity.LocalAccount
import com.example.data.local.entity.LocalCategory
import com.example.data.local.entity.LocalTransaction
import com.example.data.network.model.account.AccountBrief
import com.example.data.network.model.category.Category
import com.example.data.network.model.transaction.Transaction
import com.example.data.network.model.transaction.TransactionRequest
import com.example.data.network.model.transaction.TransactionResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private fun getIsoDateFormat(): SimpleDateFormat {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format
}

private fun getCurrentUtcTimestamp(): Long = System.currentTimeMillis()

fun TransactionResponse.toLocalTransaction(isSynced: Boolean = true): LocalTransaction {
    val parsedCreatedAt = try {
        getIsoDateFormat().parse(this.createdAt)?.time ?: getCurrentUtcTimestamp()
    } catch (e: Exception) {
        getCurrentUtcTimestamp()
    }
    val parsedUpdatedAt = try {
        getIsoDateFormat().parse(this.updatedAt)?.time ?: getCurrentUtcTimestamp()
    } catch (e: Exception) {
        getCurrentUtcTimestamp()
    }

    return LocalTransaction(
        id = this.id,
        accountId = this.account.id,
        categoryId = this.category.id,
        amount = this.amount.toDoubleOrNull() ?: 0.0,
        transactionDate = this.transactionDate,
        comment = this.comment,
        isSynced = isSynced,
        createdAt = parsedCreatedAt,
        updatedAt = parsedUpdatedAt
    )
}


fun LocalTransaction.toTransactionRequest(): TransactionRequest {
    return TransactionRequest(
        accountId = this.accountId,
        categoryId = this.categoryId,
        amount = this.amount.toString(),
        transactionDate = this.transactionDate,
        comment = this.comment
    )
}

 fun LocalTransaction.toTransactionResponse(account: AccountBrief, category: Category): TransactionResponse =
     TransactionResponse(
         id = this.id,
         account = account,
         category = category,
         amount = this.amount.toString(),
         transactionDate = this.transactionDate,
         comment = this.comment,
         createdAt = getIsoDateFormat().format(Date(this.createdAt)),
         updatedAt = getIsoDateFormat().format(Date(this.updatedAt))
     )

fun Transaction.toLocalTransaction(isSynced: Boolean = true): LocalTransaction {
    val parsedCreatedAt = try {
        getIsoDateFormat().parse(this.createdAt)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) { System.currentTimeMillis() }
    val parsedUpdatedAt = try {
        getIsoDateFormat().parse(this.updatedAt)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) { System.currentTimeMillis() }

    return LocalTransaction(
        id = this.id,
        accountId = this.accountId,
        categoryId = this.categoryId,
        amount = this.amount.toDoubleOrNull() ?: 0.0,
        transactionDate = this.transactionDate,
        comment = this.comment,
        isSynced = isSynced,
        createdAt = parsedCreatedAt,
        updatedAt = parsedUpdatedAt
    )
}

fun Transaction.toLocalTransactionFromPost(
    originalRequest: TransactionRequest,
    isSynced: Boolean = true
): LocalTransaction {
    val serverAmount = this.amount
    val serverTransactionDate = this.transactionDate
    val serverComment = this.comment
    val serverCreatedAt = this.createdAt
    val serverUpdatedAt = this.updatedAt

    val parsedCreatedAt = try {
        getIsoDateFormat().parse(serverCreatedAt)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) { System.currentTimeMillis() }
    val parsedUpdatedAt = try {
        getIsoDateFormat().parse(serverUpdatedAt)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) { System.currentTimeMillis() }


    return LocalTransaction(
        id = this.id,
        accountId = originalRequest.accountId,
        categoryId = originalRequest.categoryId,
        amount = serverAmount.toDoubleOrNull() ?: 0.0,
        transactionDate = serverTransactionDate,
        comment = serverComment,
        isSynced = isSynced,
        createdAt = parsedCreatedAt,
        updatedAt = parsedUpdatedAt
    )
}

fun LocalAccount.toAccountBriefNetwork(): com.example.data.network.model.account.AccountBrief {
    return com.example.data.network.model.account.AccountBrief(this.id, this.name, this.balance, this.currency)
}
fun LocalCategory.toCategoryNetwork(): com.example.data.network.model.category.Category {
    return com.example.data.network.model.category.Category(this.id, this.name, this.emoji, this.isIncome)
}