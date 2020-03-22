package com.jvmori.myapplication.util

import androidx.room.Room
import com.jvmori.myapplication.common.data.PhotosDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseTestModule = module {
    single(override = true) {
        Room.inMemoryDatabaseBuilder(androidApplication().applicationContext, PhotosDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}