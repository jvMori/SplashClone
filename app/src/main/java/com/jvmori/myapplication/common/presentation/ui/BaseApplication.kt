package com.jvmori.myapplication.common.presentation.ui

import android.app.Application
import com.jvmori.myapplication.collectionslist.presentation.di.collectionsModule
import com.jvmori.myapplication.common.data.databaseModule
import com.jvmori.myapplication.common.presentation.di.networkModule
import com.jvmori.myapplication.photoslist.presentation.di.photosModule
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
                    collectionsModule,
                    databaseModule
                )
            )
        }
    }
}