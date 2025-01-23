package com.miguel.tibiamap

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //androidLogger()
            androidContext(this@MainApplication)
            //added Koin modules
            modules(Di().appModule)
        }
    }
}