package com.jvmori.myapplication.search.presentation.viemodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.repositories.SearchDataSource
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchViewModel : BaseSearchViewModel() {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val photosDataSource: PhotosDataSource = searchScope.get { parametersOf(viewModelScope) }
    private val factory: PhotosDataSourceFactory = searchScope.get { parametersOf(photosDataSource) }
    private val collectionsDataSource: SearchDataSource<CollectionEntity> = searchScope.get { parametersOf(viewModelScope) }

    fun setPhotoOrientation(orientation: String) {
        if (photoParams.query.isNotEmpty()) {
            photoParams.orientation = orientation.toLowerCase()
            this.photoParamsObservable.value = photoParams
        }
    }

    fun getPhotos(): LiveData<PagedList<PhotoEntity>> {
        return Transformations.switchMap(photoParamsObservable) {
            factory.setPhotoParams(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    fun getCollections(): LiveData<PagedList<CollectionEntity>> {
        return Transformations.switchMap(photoParamsObservable) {
            collectionsDataSource.query = it.query
            LivePagedListBuilder<Int, CollectionEntity>(getFactory(collectionsDataSource), config).build()
        }
    }

    fun getPhotosRetryAction() {
        photosDataSource.retryAction?.invoke()
    }

    fun getCollectionsRetryAction() {
        collectionsDataSource.retryAction?.invoke()
    }

    val photosNetworkStatus: LiveData<Resource.Status> by lazy {
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }

    val collectionsNetworkStatus: LiveData<Resource.Status> by lazy {
        collectionsDataSource.networkStatus
    }

}