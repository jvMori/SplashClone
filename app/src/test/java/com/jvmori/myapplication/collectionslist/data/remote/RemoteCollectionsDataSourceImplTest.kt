package com.jvmori.myapplication.collectionslist.data.remote

import android.accounts.NetworkErrorException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RemoteCollectionsDataSourceImplTest {

    @Mock
    lateinit var api: CollectionsApi

    private lateinit var networkResponse: CollectionsResponse

    private lateinit var remoteCollectionsDataSource: RemoteCollectionsDataSource

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkResponse = CollectionsResponse()
        remoteCollectionsDataSource =
            RemoteCollectionsDataSourceImpl(api)
    }

    @Test
    fun `getCollections when success return data and success`() {
        runBlocking {
            //Arrange
            val data = listOf(networkResponse)
            Mockito.`when`(api.getCollections(1)).thenReturn(data)

            //Act
            val result = remoteCollectionsDataSource.getCollections(1)

            //Assert
            Assert.assertEquals(result.status, Resource.Status.SUCCESS)
            Assert.assertNotNull(result.data)
        }
    }

    @Test
    fun `getCollections when network error then return failure`() {
        runBlocking {
            //Arrange
            Mockito.`when`(api.getCollections(1)).thenAnswer {
                throw NetworkErrorException()
            }

            //Act
            val result = remoteCollectionsDataSource.getCollections(1)

            //Assert
            Assert.assertEquals(result.status, Resource.Status.NETWORK_ERROR)
            Assert.assertNull(result.data)
        }
    }

    @Test
    fun `getCollections when general error then return failure`() {
        runBlocking {
            //Arrange
            Mockito.`when`(api.getCollections(1)).thenAnswer {
                throw Exception()
            }

            //Act
            val result = remoteCollectionsDataSource.getCollections(1)

            //Assert
            Assert.assertEquals(result.status, Resource.Status.ERROR)
            Assert.assertNull(result.data)
        }
    }
}