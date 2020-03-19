package com.jvmori.myapplication.photoslist.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.jvmori.myapplication.TestCoroutineRule
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.Parameters
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.photoslist.domain.usecases.RefreshPhotosUseCase
import com.jvmori.myapplication.photoslist.presentation.viewmodels.PhotosViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


class PhotosViewModelTest {

    private lateinit var viewmodel: PhotosViewModel
    private val getPhotosUseCase = mock(GetPhotosListUseCase::class.java)
    private val refreshPhotosUseCase = mock(RefreshPhotosUseCase::class.java)

    @Mock
    private lateinit var viewStateObserver: Observer<Resource.Status>

    @Mock
    lateinit var pagedParams : PageKeyedDataSource.LoadInitialParams<Int>

    @Mock
    lateinit var callback : PageKeyedDataSource.LoadInitialCallback<Int, PhotoEntity>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewmodel = PhotosViewModel(
            getPhotosUseCase,
            refreshPhotosUseCase
        ).apply {
            networkStatus.observeForever(viewStateObserver)
        }
    }

    @Test
    fun getPhotos_when_order_popular_returns_popular_photos() {
        testCoroutineRule.runBlockingTest {
            //Arrange
            val params = Parameters(1, Order.popular.toString())
            val photos = listOf(PhotoEntity("1", "", 1))
            val data = Resource.success(photos)
            viewmodel.photoDataSource = mock(PhotosDataSource::class.java)

            viewmodel.changeOrder(Order.popular)
            `when`(getPhotosUseCase.getPhotos(params.page, params.order)).thenReturn(data)

            //Act
            viewmodel.photos
            viewmodel.networkStatus

            //Verify
            //verify(viewStateObserver).onChanged(Resource.Status.SUCCESS)
        }
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

