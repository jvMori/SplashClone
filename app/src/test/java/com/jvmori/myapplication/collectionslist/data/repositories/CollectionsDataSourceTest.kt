package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.collectionslist.data.usecases.GetCollectionsUseCaseImpl
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CollectionsDataSourceTest {

    private val scope = TestCoroutineScope()
    private lateinit var collectionsDataSource: CollectionsDataSource
    private val useCase: GetCollectionsUseCase = mock(GetCollectionsUseCaseImpl::class.java)
    @Mock
    private lateinit var stateObserver: Observer<Resource.Status>
    @Mock
    private lateinit var params: PageKeyedDataSource.LoadInitialParams<Int>
    @Mock
    private lateinit var callback: PageKeyedDataSource.LoadInitialCallback<Int, CollectionEntity>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        collectionsDataSource = CollectionsDataSource(scope, useCase).apply {
            networkStatus.observeForever(stateObserver)
        }
    }

    @Test
    fun `load initial when success return success status`() {
        scope.runBlockingTest {
            //Arrange
            val data = flow {
                emit(Resource.success(listOf(CollectionEntity(1))))
            }
            `when`(useCase.getCollections(1)).thenReturn(data)

            //Act
            collectionsDataSource.loadInitial(params, callback)

            //Assert
            verify(stateObserver).onChanged(Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `load initial when network exception return network error status`() {
        scope.runBlockingTest {
            //Arrange
            val data: Flow<Resource<List<CollectionEntity>>> = flow {
                emit(Resource.networkError(listOf(), ""))
            }
            `when`(useCase.getCollections(1)).thenReturn(data)

            //Act
            collectionsDataSource.loadInitial(params, callback)

            //Assert
            verify(stateObserver).onChanged(Resource.Status.NETWORK_ERROR)
        }
    }
}