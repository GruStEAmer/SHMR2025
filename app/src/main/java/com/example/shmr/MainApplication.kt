package com.example.shmr

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.account.di.deps.AccountDepsStore
import com.example.categories.di.deps.CategoriesDepsStore
import com.example.expenses.di.deps.ExpensesDepsStore
import com.example.income.di.deps.IncomeDepsStore
import com.example.shmr.di.AppComponent
import com.example.shmr.di.DaggerAppComponent
import com.example.shmr.worker.SyncWorker
import com.example.shmr.worker.di.DaggerWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainApplication: Application(), Configuration.Provider {

    companion object {
        const val TAG_APP = "MainApplication"
    }

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var daggerWorkerFactory: DaggerWorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(this)
        appComponent.inject(this)

        CategoriesDepsStore.categoriesDeps = appComponent
        AccountDepsStore.accountDeps = appComponent
        ExpensesDepsStore.expensesDeps = appComponent
        IncomeDepsStore.incomeDeps = appComponent

        schedulePeriodicDataSync(this)
        registerNetworkCallback()
    }
    override val workManagerConfiguration: Configuration
        get() {
            if (::daggerWorkerFactory.isInitialized) {
                Log.d("MainApplication", "WorkManagerConfiguration: daggerWorkerFactory is Initialized. Using it.")
            } else {
                Log.e("MainApplication", "WorkManagerConfiguration: ERROR - daggerWorkerFactory is NOT Initialized! Default factory will be used.")
            }
            return Configuration.Builder()
                .setWorkerFactory(daggerWorkerFactory)
                .setMinimumLoggingLevel(Log.INFO)
                .build()
        }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d(TAG_APP, "Сеть доступна. Планирование одноразовой синхронизации через NetworkCallback.")
                scheduleSyncOnNetworkConnect(applicationContext)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d(TAG_APP, "Сеть потеряна (через NetworkCallback).")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            try {
                connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
            } catch (e: SecurityException) {
                Log.e(TAG_APP, "Не удалось зарегистрировать NetworkCallback, нет прав?", e)
             }
        }
        Log.d(TAG_APP, "NetworkCallback зарегистрирован.")
    }

    private fun scheduleSyncOnNetworkConnect(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeSyncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag(SyncWorker.ONE_TIME_WORK_TAG + "_OnConnect_NetworkCallback")
            .build()

        WorkManager.getInstance(context.applicationContext).enqueueUniqueWork(
            "UniqueSyncOnNetworkConnect_NetworkCallback",
            ExistingWorkPolicy.REPLACE,
            oneTimeSyncRequest
        )
        Log.d(TAG_APP, "Одноразовая синхронизация по появлению сети (NetworkCallback) запланирована.")
    }
    private fun schedulePeriodicDataSync(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            6, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag(SyncWorker.TAG)
            .build()

        WorkManager.getInstance(context.applicationContext).enqueueUniquePeriodicWork(
            SyncWorker.UNIQUE_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
        Log.d(SyncWorker.TAG, "Периодическая синхронизация (${SyncWorker.UNIQUE_PERIODIC_WORK_NAME}) запланирована.")
    }

}
val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApplication -> appComponent
        else -> applicationContext.appComponent
    }
