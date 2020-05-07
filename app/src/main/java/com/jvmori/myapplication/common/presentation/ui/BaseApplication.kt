package com.jvmori.myapplication.common.presentation.ui

import android.app.Application
import com.jvmori.myapplication.collectionslist.presentation.di.collectionsModule
import com.jvmori.myapplication.common.data.local.databaseModule
import com.jvmori.myapplication.common.presentation.di.imageModule
import com.jvmori.myapplication.common.presentation.di.networkModule
import com.jvmori.myapplication.common.presentation.di.pagingModule
import com.jvmori.myapplication.photoslist.presentation.di.photosModule
import com.jvmori.myapplication.search.presentation.di.baseSearchModule
import com.jvmori.myapplication.search.presentation.di.searchModule
import com.jvmori.myapplication.search.presentation.di.usersModule
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
                    databaseModule,
                    pagingModule,
                    imageModule,
                    searchModule,
                    usersModule,
                    baseSearchModule
                )
            )
        }
    }
}