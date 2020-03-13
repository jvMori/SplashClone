package com.jvmori.myapplication.data.repositories

import com.jvmori.myapplication.common.data.NetworkBoundResource
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.data.repositories.Parameters
import com.jvmori.myapplication.photoslist.data.repositories.PhotosRepositoryImpl
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PhotosRepositoryImplTest {

    private val local: LocalPhotosDataSource = mock(LocalPhotosDataSource::class.java)
    private val remote: RemotePhotosDataSource = mock(RemotePhotosDataSource::class.java)
    private val repository: PhotosRepository by lazy { PhotosRepositoryImpl(remote, local) }

    private val params: Parameters = Parameters(1, Order.popular.toString())
    private val data = listOf(mock(PhotoData::class.java))
    private val resultData = listOf(mock(PhotoEntity::class.java))
    private val repo: NetworkBoundResource<List<PhotoData>, List<PhotoDataResponse>, List<PhotoEntity>, Parameters> by lazy {
        PhotosRepositoryImpl(remote, local)
    }

    @Test
    fun getPhotos_success_successReturned() {
        runBlocking {
            //Arrange
            val data = listOf(mock(PhotoEntity::class.java))
            `when`(repository.getPhotos(params.page, params.order))
                .thenReturn(Resource.success(data))

            //Act
            val result = repository.getPhotos(params.page, params.order)

            //Assert
            assert(result.data == data)
            assert(result.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun getPhotos_error_failureReturned() {
        runBlocking {
            //Arrange
            `when`(repository.getPhotos(params.page, params.order))
                .thenReturn(Resource.error("", null))

            //Act
            val result = repository.getPhotos(params.page, params.order)

            //Assert
            assert(result.data == null)
            assert(result.status == Resource.Status.ERROR)
        }
    }

    @Test
    fun getPhotos_refreshNeeded_returnFreshData() {
        runBlocking {
            //Arrange
            doReturn(true).`when`(repo).refreshNeeded(data)
            `when`(repo.fetchData(params)).thenReturn(Resource.success(resultData))

            //Act
            val result = repo.fetchData(params)

            //Assert
            //verify(repo, times(1)).networkRequest(params)
            //verify(repo, times(1)).saveCallResult(data)
            assert(result.data?.isNotEmpty() ?: result.data != null)
        }
    }
}