package com.jvmori.myapplication.collectionslist.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    fun `getCollections when success return flow`() {
        runBlocking {
            //Act
            val result = remoteCollectionsDataSource.getCollections(1)

            //Assert
            Assert.assertNotNull(result)
        }
    }
}