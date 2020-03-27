package com.jvmori.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.*
import org.mockito.Mockito.*


class PhotosViewModelTest : KoinTest {

    private lateinit var viewmodel: PhotosViewModel
    @ExperimentalCoroutinesApi
    private val testCoroutineScope = TestCoroutineScope()
    @Mock
    private lateinit var getPhotos : GetPhotosListUseCase
    private val photosDataSource: PhotosDataSource by inject { parametersOf(testCoroutineScope, Order.popular, getPhotos) }
    private val photoDataSourceFactory: PhotosDataSourceFactory by inject { parametersOf(photosDataSource) }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(viewModeltestModule)
    }

    @Test
    fun getPhotos_when_order_popular_returns_popular_photos() {
        testCoroutineRule.runBlockingTest {
            //Arrange
            val data = listOf(PhotoEntity("0", "", 1))
            val excepected = mockPagedList(data)
            `when`(getPhotos.getPhotos(1, Order.popular.toString())).thenReturn(Resource.success(data))

            //Act
            val result = viewmodel.fetchPhotos(Order.popular)

            //Verify
            assertEquals(result?.value, excepected)
        }
    }

    val viewModeltestModule = module {
        single<PhotosDataSource>(override = true) { (scope: CoroutineScope, order: Order, getPhotos : GetPhotosListUseCase) ->
            PhotosDataSource(scope, getPhotos, order.toString())
        }
        single(override = true) { (photosDataSource: PhotosDataSource) -> PhotosDataSourceFactory(photosDataSource) }
    }

    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}

