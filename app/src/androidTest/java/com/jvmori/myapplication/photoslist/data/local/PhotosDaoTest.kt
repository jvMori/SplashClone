package com.jvmori.myapplication.photoslist.data.local

import androidx.room.Room
import com.jvmori.myapplication.common.data.PhotosDatabase
import com.jvmori.myapplication.photoslist.data.remote.Order
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class PhotosDaoTest : KoinTest {

    val photosDatabase : PhotosDatabase by inject()
    val photosDao : PhotosDao by inject()

    @Before
    fun before() {
        loadKoinModules(databaseTestModule)
    }

    @After
    fun after() {
        photosDatabase.close()
    }

    @Test
    fun when_saving_photos_in_db_return_success(){
        //Arrange
        var results = listOf<PhotoData>()
        val photoData1 = PhotoData(1, Order.popular.toString(), "1")
        val photoData2 = PhotoData(1, Order.popular.toString(), "2")
        val expectedPhotos = listOf(photoData1, photoData2)

        //Act
        runBlocking {
            photosDao.updatePhotos(expectedPhotos)
            results = photosDao.getPhotos(1, Order.popular.toString())
        }

        //Assert
        Assert.assertEquals(results, expectedPhotos)
    }
}

val databaseTestModule = module {
    single(override = true) {
        Room.inMemoryDatabaseBuilder(androidApplication().applicationContext, PhotosDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}