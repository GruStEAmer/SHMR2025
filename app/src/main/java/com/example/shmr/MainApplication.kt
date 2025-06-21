package com.example.shmr

import android.app.Application
import com.example.shmr.di.AppContainer
import com.example.shmr.di.DefaultAppContainer

class MainApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()
    }
}
