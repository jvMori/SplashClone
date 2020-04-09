package com.jvmori.myapplication.collectionslist.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RemoteCollectionsDataSourceImplTest {

    lateinit var api: CollectionsApi
    private lateinit var networkResponse: CollectionsResponse
    private lateinit var remoteCollectionsDataSource: RemoteCollectionsDataSource

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkResponse = CollectionsResponse()
        api = FakeCollectionsApi(networkResponse)
        remoteCollectionsDataSource = RemoteCollectionsDataSourceImpl(api)
    }

    @Test
    fun `getCollections when success return success result`() {
        runBlocking {
            //Act
            val result = async {
                remoteCollectionsDataSource.getCollections(1).take(1).firstOrNull()
            }

            //Assert
            Assert.assertTrue(result.await()?.status == Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `getCollections when network error return network failure result`() {
        runBlocking {
            //Arrange
            //api = FakeCollectionsApi(networkResponse, true)

            //Act
            val result = async {
                remoteCollectionsDataSource.getCollections(1).take(1).firstOrNull()
            }

            //Assert
            Assert.assertTrue(result.await()?.status == Resource.Status.NETWORK_ERROR)
        }
    }
}
