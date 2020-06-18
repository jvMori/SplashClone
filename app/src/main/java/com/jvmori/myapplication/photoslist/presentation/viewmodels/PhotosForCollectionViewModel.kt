package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
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

class PhotosForCollectionViewModel : BasePhotosViewModel(), KoinComponent {

    override var photos: LiveData<PagedList<PhotoEntity>>? = null
    private val config by inject<PagedList.Config>()

    private val scope = getKoin().getOrCreateScope(
        photosForCollectionsId, named(
            photosForCollections
        ))
    private val dataSource: PhotosDataSource = scope.get { parametersOf(viewModelScope) }
    private var factory: PhotosDataSourceFactory = scope.get { parametersOf(dataSource) }

    override fun fetchPhotos(id: Int?) {
        dataSource.apply {
            photoParams.collectionId = id ?: 0
            photos = LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    override var networkStatus: LiveData<Resource.Status>? =
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )

    override fun retryAction() {
        dataSource.retryAction?.invoke()
    }
}