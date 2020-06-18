package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotosViewModel : BasePhotosViewModel(), KoinComponent {

    private val config by inject<PagedList.Config>()
    override var photos: LiveData<PagedList<PhotoEntity>>? = null
    val order: MutableLiveData<Order> = MutableLiveData()

    private val photosDataSource: PhotosDataSource by inject { parametersOf(viewModelScope) }
    private var factory: PhotosDataSourceFactory = get { parametersOf(photosDataSource) }

    override var networkStatus: LiveData<Resource.Status>? =
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )

    override fun fetchPhotos(id: Int?) {
        photos = Transformations.switchMap(order) {
            factory.setOrder(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    override fun retryAction() {
        photosDataSource.retryAction?.invoke()
    }

    fun changeOrder(order: Order) {
        this.order.value = order
    }

}