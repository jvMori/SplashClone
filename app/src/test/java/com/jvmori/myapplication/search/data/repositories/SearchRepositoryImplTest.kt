package com.jvmori.myapplication.search.data.repositories

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.users.domain.UserEntity
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class SearchRepositoryImplTest {

    private lateinit var searchRepository: SearchRepository

    @Mock
    lateinit var remoteDataSource: SearchRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchRepository = SearchRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when search photos success then return success object`() {
        runBlocking {
            //arrange
            val params = PhotoParams("", 1)
            Mockito.`when`(remoteDataSource.searchPhotos(params)).thenReturn(listOf())

            //act
            val result = searchRepository.searchPhotos(params)

            //assert
            assert(result.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `when search photos network error then return network error object`() {
        runBlocking {
            //arrange
            val params = PhotoParams("", 1)
            Mockito.`when`(remoteDataSource.searchPhotos(params)).then {
                throw NetworkErrorException()
            }

            //act
            val result = searchRepository.searchPhotos(params)

            //assert
            assert(result.status == Resource.Status.NETWORK_ERROR)
        }
    }

    @Test
    fun `when search photos general error then return error object`() {
        runBlocking {
            //arrange
            val params = PhotoParams("", 1)
            Mockito.`when`(remoteDataSource.searchPhotos(params)).then {
                throw IOException()
            }

            //act
            val result = searchRepository.searchPhotos(params)

            //assert
            assert(result.status == Resource.Status.ERROR)
        }
    }

    @Test
    fun `when search users success then return success object`() {
        runBlocking {
            //arrange
            Mockito.`when`(remoteDataSource.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenReturn(listOf(UserEntity()))

            //act
            val result = searchRepository.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `when search users network exception then return error object`() {
        runBlocking {
            //arrange
            Mockito.`when`(remoteDataSource.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenAnswer {
                    throw NetworkErrorException()
                }

            //act
            val result: Resource<List<UserEntity>> =
                searchRepository.searchUsers(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.status == Resource.Status.NETWORK_ERROR)
        }
    }

    @Test
    fun `when search collections success then return success object`() {
        runBlocking {
            //arrange
            Mockito.`when`(remoteDataSource.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenReturn(listOf(CollectionEntity(0)))

            //act
            val result = searchRepository.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `when search collections network exception then return error object`() {
        runBlocking {
            //arrange
            Mockito.`when`(remoteDataSource.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt()))
                .thenAnswer {
                    throw NetworkErrorException()
                }

            //act
            val result =
                searchRepository.searchCollections(ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())

            //assert
            assert(result.status == Resource.Status.NETWORK_ERROR)
        }
    }
}