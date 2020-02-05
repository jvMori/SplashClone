package com.jvmori.myapplication.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.data.remote.Order
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.repositories.PhotosDataSource
import com.jvmori.myapplication.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import com.jvmori.myapplication.domain.usecases.RefreshPhotos
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class PhotosViewModel(
    private val photosList: GetPhotosList,
    private val refreshPhotos: RefreshPhotos
) : ViewModel() {

    private val pageSize = 10
    val order: MutableLiveData<Order> = MutableLiveData()
    var networkStatus: LiveData<Resource.Status> = MutableLiveData()

    val photos: LiveData<PagedList<PhotoEntity>> = Transformations.switchMap(order) { input: Order? ->
        val photoDataSourceFactory = PhotosDataSourceFactory(viewModelScope, photosList, input.toString())
        initNetworkStatus(photoDataSourceFactory)
        LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()
    }

    private val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize)
        .setEnablePlaceholders(false)
        .build()

    fun changeOrder(order: Order) {
        this.order.value = order
    }

    private fun initNetworkStatus(photoDataSourceFactory: PhotosDataSourceFactory) {
        networkStatus = Transformations.switchMap(
            photoDataSourceFactory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }

    fun refreshPhotos() {

    }
}