package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotosViewModel : ViewModel(), KoinComponent {

    private val config by inject<PagedList.Config>()
    val order: MutableLiveData<Order> = MutableLiveData()
    val networkStatus: LiveData<Resource.Status> by lazy {
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }
    private val photosDataSource: PhotosDataSource by inject { parametersOf(viewModelScope) }
    private val factory: PhotosDataSourceFactory by inject { parametersOf(photosDataSource) }

    fun fetchPhotos(): LiveData<PagedList<PhotoEntity>> {
        return Transformations.switchMap(order) {
            factory.setOrder(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    fun changeOrder(order: Order) {
        this.order.value = order
    }

    fun retryAction() {
        photosDataSource.retryAction?.invoke()
    }
}