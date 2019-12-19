package com.jvmori.myapplication.core.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jvmori.myapplication.features.photolist.data.datasources.local.PhotosDao
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import org.koin.dsl.module

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
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
    single { (get() as PhotosDatabase).photosDao() }
}