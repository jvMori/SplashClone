package com.jvmori.myapplication.search.presentation.viemodel

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSource
import com.jvmori.myapplication.photoslist.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.data.repositories.CollectionsDataSource
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchViewModel : ViewModel(), KoinComponent {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))

    private val config by inject<PagedList.Config>()
    private val photosDataSource: PhotosDataSource = searchScope.get { parametersOf(viewModelScope) }
    private val factory: PhotosDataSourceFactory = searchScope.get { parametersOf(photosDataSource) }
    private val collectionsDataSource: CollectionsDataSource = searchScope.get { parametersOf(viewModelScope) }

    private val photoParams = MutableLiveData<PhotoParams>()
    private var _photoParams = PhotoParams("", 1)

    private val query = MutableLiveData<String>()

    fun setCollectionQuery(query: String?) {
        query?.let {
            this.query.value = it
        }
    }

    fun setPhotoOrientation(orientation: String) {
        if (_photoParams.query.isNotEmpty()) {
            _photoParams.orientation = orientation.toLowerCase()
            this.photoParams.value = _photoParams
        }
    }

    fun setPhotoQuery(query: String?) {
        query?.let {
            _photoParams.query = it
            this.photoParams.value = _photoParams
            this.query.value = query
        }
    }

    fun getPhotos(): LiveData<PagedList<PhotoEntity>> {
        return Transformations.switchMap(photoParams) {
            factory.setPhotoParams(it)
            LivePagedListBuilder<Int, PhotoEntity>(factory, config).build()
        }
    }

    fun getCollections(): LiveData<PagedList<CollectionEntity>> {
        return Transformations.switchMap(query) {
            collectionsDataSource.query = it
            LivePagedListBuilder<Int, CollectionEntity>(object : DataSource.Factory<Int, CollectionEntity>() {
                override fun create(): DataSource<Int, CollectionEntity> {
                    return collectionsDataSource
                }
            }, config).build()
        }
    }

    fun getPhotosRetryAction() {
        photosDataSource.retryAction?.invoke()
    }

    fun getCollectionsRetryAction() {
        collectionsDataSource.retryAction?.invoke()
    }

    val networkStatus: LiveData<Resource.Status> by lazy {
        Transformations.switchMap(
            factory.photosLiveData,
            PhotosDataSource::networkStatus
        )
    }

    val collectionsNetworkStatus: LiveData<Resource.Status> by lazy {
        collectionsDataSource.networkStatus
    }
}