package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.presentation.di.photosForCollections
import com.jvmori.myapplication.photoslist.presentation.di.photosForCollectionsId
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class PhotosViewModel : ViewModel(), KoinComponent {

    private val scope = getKoin().getOrCreateScope(photosForCollectionsId, named(photosForCollections))

    private val config by inject<PagedList.Config>()
    var photos: LiveData<PagedList<PhotoEntity>>? = null
    val order: MutableLiveData<Order> = MutableLiveData()
    val networkStatus: LiveData<Resource.Status> by lazy {
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }
    private val photosForCollectionsDataSource: PhotosDataSource = scope.get { parametersOf(viewModelScope) }
    private val photosDataSource: PhotosDataSource by inject { parametersOf(viewModelScope) }
    private var factory: PhotosDataSourceFactory = get { parametersOf(photosDataSource) }

    fun fetchPhotos(collectionId: Int? = null) {
        if (collectionId != null) fetchPhotosForCollection(collectionId) else fetchPopularPhotos()
    }

    private fun fetchPopularPhotos() {
        photos = Transformations.switchMap(order) {
            factory.setOrder(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    private fun fetchPhotosForCollection(id: Int) {
        photosForCollectionsDataSource.apply {
            photoParams.collectionId = id
            factory = PhotosDataSourceFactory(this)
            photos = LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    fun changeOrder(order: Order) {
        this.order.value = order
    }

    fun retryAction(id: Int?) {
        if (id == null)
            photosDataSource.retryAction?.invoke()
        else
            photosForCollectionsDataSource.retryAction?.invoke()
    }
}