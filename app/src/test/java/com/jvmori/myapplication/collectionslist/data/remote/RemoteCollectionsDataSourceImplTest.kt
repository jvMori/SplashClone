package com.jvmori.myapplication.collectionslist.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


class RemoteCollectionsDataSourceImplTest {

    lateinit var api: CollectionsApi
    lateinit var errorApi: CollectionsApi
    private lateinit var networkResponse: CollectionsResponse
    private lateinit var remoteCollectionsDataSource: RemoteCollectionsDataSource
    private lateinit var remoteErrorCollectionsDataSource: RemoteCollectionsDataSource

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        networkResponse = CollectionsResponse()
        api = FakeCollectionsApi(networkResponse)
        errorApi = FakeCollectionsApi(networkResponse, true)
        remoteCollectionsDataSource = RemoteCollectionsDataSourceImpl(api)
        remoteErrorCollectionsDataSource = RemoteCollectionsDataSourceImpl(errorApi)
    }

    @Test
    fun `getCollections when success return success result`() {
        runBlocking {
            //Act
            val result = remoteCollectionsDataSource.getCollections(1, CollectionType.DefaultCollection)

            //Assert
            Assert.assertTrue(result.isNotEmpty())
        }
    }
}
