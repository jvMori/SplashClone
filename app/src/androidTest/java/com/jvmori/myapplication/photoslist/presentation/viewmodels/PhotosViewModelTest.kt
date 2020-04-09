package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.jvmori.myapplication.TestCoroutineRule
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class PhotosViewModelTest : KoinTest {

    private lateinit var viewmodel: PhotosViewModel
    @ExperimentalCoroutinesApi
    private val testCoroutineScope = TestCoroutineScope()

    private val photosDataSource: PhotosDataSource by inject { parametersOf(testCoroutineScope, Order.popular) }
    private val photoDataSourceFactory: PhotosDataSourceFactory by inject { parametersOf(photosDataSource) }

    private var getPhotos: GetPhotosListUseCase = FakeGetPhotosListUseCase()

    @Mock
    private lateinit var stateObserver: Observer<PagedList<PhotoEntity>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(viewModelTestModule)
        viewmodel = PhotosViewModel()
    }

    @Test
    fun getPhotos_when_order_popular_returns_popular_photos() {
        runBlocking {
            //Arrange
            val data = listOf(PhotoEntity("0", "", 1))
            val excepted = mockPagedList(data)

            //Act
            val result = viewmodel.fetchPhotos()
            result.observeForever(stateObserver)

            //Verify
            verify(stateObserver).onChanged(excepted)
        }
    }

    private val viewModelTestModule = module {
        single(override = true) { (scope: CoroutineScope) ->
            PhotosDataSource(scope, getPhotos)
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

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

