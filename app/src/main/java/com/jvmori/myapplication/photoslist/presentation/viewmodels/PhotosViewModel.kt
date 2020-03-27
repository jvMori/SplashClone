package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.photoslist.domain.usecases.RefreshPhotosUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotosViewModel : ViewModel(), KoinComponent {

    private val pageSize = 10
    val order: MutableLiveData<Order> = MutableLiveData()
    var networkStatus: LiveData<Resource.Status> = MutableLiveData()
    private val photosDataSource: PhotosDataSource by inject { parametersOf(viewModelScope, Order.popular) }
    private val photoDataSourceFactory: PhotosDataSourceFactory by inject { parametersOf(photosDataSource) }

    val photos: LiveData<PagedList<PhotoEntity>> = Transformations.switchMap(order) { input: Order? ->
        //        photoDataSource = PhotosDataSource(
//            viewModelScope,
//            getPhotosListUseCase,
//            input.toString()
//        )

        initNetworkStatus(photoDataSourceFactory)
        LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()
    }

    fun fetchPhotos(order: Order): LiveData<PagedList<PhotoEntity>> {
        initNetworkStatus(photoDataSourceFactory)
        return LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()
    }

    private val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize)
        .setEnablePlaceholders(false)
        .build()

    fun changeOrder(order: Order) {
        this.order.value = order
    }

    fun initNetworkStatus(photoDataSourceFactory: PhotosDataSourceFactory) {
        networkStatus = Transformations.switchMap(
            photoDataSourceFactory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }

    fun refreshPhotos() {

    }
}