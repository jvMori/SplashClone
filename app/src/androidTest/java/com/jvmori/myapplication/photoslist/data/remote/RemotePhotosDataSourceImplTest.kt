package com.jvmori.myapplication.photoslist.data.remote

import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.data.repositories.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class RemotePhotosDataSourceImplTest{

    val api = mock(Api::class.java)
    val remoteDataSource = RemotePhotosDataSourceImpl(api)

    @Test
    fun getPhotos_when_success_return_data(){
        runBlocking {
            //Arrange
            val data = listOf(PhotoDataResponse())
            val params = Parameters(1, Order.popular.toString())
            `when`(api.getPhotos(params.page, params.order)).thenReturn(data)

            //Act
            val result = remoteDataSource.getPhotos(params.page, params.order)

            //Assert
            Assert.assertEquals(data, result)
        }
    }
}