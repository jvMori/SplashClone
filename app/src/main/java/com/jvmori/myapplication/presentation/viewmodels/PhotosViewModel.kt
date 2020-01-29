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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PhotosViewModel(
    private val photosList: GetPhotosList,
    private val refreshPhotos: RefreshPhotos
) : ViewModel() {

    private val pageSize = 10
    private var order: Flow<String> = flow {
        emit(Order.popular.toString())
    }

    fun setOrder(order: Order) {
        flow {
            emit(order)
        }
    }

    private val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize)
        .setEnablePlaceholders(false)
        .build()

    private var photoDataSourceFactory = PhotosDataSourceFactory(viewModelScope, photosList)

    var photos: LiveData<PagedList<PhotoEntity>> =
        LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()

    val networkStatus: LiveData<Resource.Status> = Transformations.switchMap(
        photoDataSourceFactory.photosLiveData,
        PhotosDataSource::networkStatus
    )

    fun fetchPhotos() {
        viewModelScope.launch {
            order.map {
                photoDataSourceFactory = PhotosDataSourceFactory(viewModelScope, photosList, it)
                photos = LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()
            }.collect()
        }
    }

    fun refreshPhotos() {

    }
}