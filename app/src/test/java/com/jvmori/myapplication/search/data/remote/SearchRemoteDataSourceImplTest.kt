package com.jvmori.myapplication.search.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.data.remote.response.User
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.Orientation
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.entities.UserEntity
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchRemoteDataSourceImplTest {

    private lateinit var searchRemoteDataSource: SearchRemoteDataSource

    @Mock
    lateinit var api: SearchApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchRemoteDataSource = SearchRemoteDataSourceImpl(api)
    }

    @Test
    fun `when search photos success then return list of data`() {
        runBlocking {
            //arrange
            val data = SearchPhotosResponse(listOf(PhotoDataResponse()))
            Mockito.`when`(api.searchPhotos(ArgumentMatchers.anyMap())).thenReturn(data)

            //act
            val params = PhotoParams("", 1, Orientation.All.toString())
            val result = searchRemoteDataSource.searchPhotos(params)

            //assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun `when search photos network error then throw exception`() {
        runBlocking {
            Mockito.`when`(api.searchPhotos(ArgumentMatchers.anyMap())).then {
                throw NetworkErrorException()
            }

            //act
            val params = PhotoParams("", 1, Orientation.All.toString())
            val result: List<PhotoEntity> = try {
                searchRemoteDataSource.searchPhotos(params)
            } catch (e: NetworkErrorException) {
                listOf<PhotoEntity>()
            }

            //assert
            assert(result.isEmpty())
        }
    }

    @Test
    fun `when search collections success then return list of data`() {
        runBlocking {
            //arrange
            val data = SearchCollectionsResponse(listOf(CollectionsResponse()))
            Mockito.`when`(api.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenReturn(data)

            //act
            val result =
                searchRemoteDataSource.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun `when search collections network error then throw exception`() {
        runBlocking {
            Mockito.`when`(api.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).then {
                throw NetworkErrorException()
            }

            //act
            val result: List<CollectionEntity> = try {
                searchRemoteDataSource.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } catch (e: NetworkErrorException) {
                listOf<CollectionEntity>()
            }

            //assert
            assert(result.isEmpty())
        }
    }

    @Test
    fun `when search users success then return list of data`() {
        runBlocking {
            //arrange
            val data = SearchUsersResponse(listOf(User()))
            Mockito.`when`(api.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenReturn(data)

            //act
            val result =
                searchRemoteDataSource.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun `when search users network error then throw exception`() {
        runBlocking {
            Mockito.`when`(api.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).then {
                throw NetworkErrorException()
            }

            //act
            val result: List<UserEntity> = try {
                searchRemoteDataSource.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())
            } catch (e: NetworkErrorException) {
                listOf<UserEntity>()
            }

            //assert
            assert(result.isEmpty())
        }
    }
}