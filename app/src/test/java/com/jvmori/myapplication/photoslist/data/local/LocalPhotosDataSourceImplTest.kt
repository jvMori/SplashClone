package com.jvmori.myapplication.photoslist.data.local

import androidx.room.FtsOptions
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class LocalPhotosDataSourceImplTest{

    val photosDao = mock(PhotosDao::class.java)
    val localDataSource = LocalPhotosDataSourceImpl(photosDao)

    @Test
    fun getPhotos_when_success_return_data(){
        runBlocking {
            //Arrange
            val params = Parameters(1, Order.popular.toString())
            val localData = listOf(PhotoData(params.page, 0, params.order, "1"))
            `when`(photosDao.getPhotos(1, Order.popular.toString())).thenReturn(localData)

            //Act
            val result = localDataSource.getPhotos(params.page, params.order)

            //Assert
            Assert.assertEquals(localData, result)
        }
    }
}