package com.jvmori.myapplication.collectionslist.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvmori.myapplication.collectionslist.data.remote.CollectionsApi
import com.jvmori.myapplication.collectionslist.data.remote.CollectionsRemoteDataSourceImpl
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRemoteDataSource
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class CollectionsRemoteDataSourceImplTest{

    @Mock
    lateinit var api : CollectionsApi

    lateinit var networkResponse : CollectionsResponse

    private lateinit var remoteDataSource : CollectionsRemoteDataSource

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        networkResponse = CollectionsResponse()
        remoteDataSource =
            CollectionsRemoteDataSourceImpl(api)
    }

    @Test
    fun getCollections_when_success_return_data_and_success(){
        runBlocking {
            //Arrange
            val data = listOf(networkResponse)
            Mockito.`when`(api.getCollections(1)).thenReturn(data)

            //Act
            val result = remoteDataSource.getCollections(1)

            //Assert
            Assert.assertEquals(result.status, Resource.Status.SUCCESS)
            Assert.assertNotNull(result.data)
        }
    }
}