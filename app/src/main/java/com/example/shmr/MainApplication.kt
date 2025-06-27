package com.example.shmr

import android.app.Application
import com.example.shmr.app.di.AppContainer
import com.example.shmr.app.di.DefaultAppContainer

class MainApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()
    }
}
