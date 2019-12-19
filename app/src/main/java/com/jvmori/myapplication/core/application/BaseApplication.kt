package com.jvmori.myapplication.core.application

import android.app.Application
import com.jvmori.myapplication.core.data.db.databaseModule
import com.jvmori.myapplication.di.networkModule
import com.jvmori.myapplication.di.photosModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    networkModule,
                    photosModule,
                    databaseModule
                )
            )
        }
    }
}