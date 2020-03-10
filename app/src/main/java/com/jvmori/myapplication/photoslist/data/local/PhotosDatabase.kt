package com.jvmori.myapplication.photoslist.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.dsl.module

@Database(entities = [PhotoData::class], version = 5, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PhotosDatabase::class.java,
            "splashCloneDb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}