package com.jvmori.myapplication.photoslist.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.data.remote.photodata.Urls
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class PhotosRepositoryImplTest {

    private val local = mock(LocalPhotosDataSource::class.java)
    private val remote = mock(RemotePhotosDataSource::class.java)
    private val repository: PhotosRepository by lazy { PhotosRepositoryImpl(remote, local) }
    private val params: Parameters = Parameters(1, Order.popular.toString())
    private val photoData = PhotoData(1, "", "", Urls())

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getPhotos_should_return_success_when_fetching_data() {
        runBlocking {
            //Arrange
            val data = listOf(photoData)
            `when`(local.getPhotos(params.page, params.order)).thenReturn(data)

            //Act
            val result = repository.getPhotos(params.page, params.order)

            //Assert
            assert(result.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun getPhotos_should_return_failure_when_error() {
        runBlocking {
            //Arrange
            `when`(local.getPhotos(params.page, params.order)).thenReturn(null)

            //Act
            val result = repository.getPhotos(params.page, params.order)

            //Assert
            assert(result.status == Resource.Status.ERROR)
        }
    }

    @Test
    fun getPhotos_refreshNeeded_returnFreshData() {
        runBlocking {
            //Arrange
            val emptyList = listOf<PhotoData>()
            val networkData = PhotoDataResponse()
            val networkDataList = listOf(networkData)
            `when`(local.getPhotos(params.page, params.order)).thenReturn(emptyList)
            `when`(remote.getPhotos(params.page, params.order)).thenReturn(networkDataList)

            //Act
            val result = repository.getPhotos(params.page, params.order)

            //Assert
            assert(result.data?.isNotEmpty() ?: result.data != null)
            assert(result.status == Resource.Status.SUCCESS)
        }
    }
}