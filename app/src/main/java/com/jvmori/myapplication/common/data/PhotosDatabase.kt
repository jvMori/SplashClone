package com.jvmori.myapplication.common.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jvmori.myapplication.collectionslist.data.local.CollectionsDao
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.local.PhotosDao
import org.koin.dsl.module

@Database(entities = [PhotoData::class], version = 6, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
    abstract fun collectionsDao(): CollectionsDao
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