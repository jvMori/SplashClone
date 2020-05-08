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
import com.jvmori.myapplication.search.domain.entities.UserEntity
import com.jvmori.myapplication.search.presentation.di.searchModuleNamed
import com.jvmori.myapplication.search.presentation.di.searchUsersId
import com.jvmori.myapplication.search.presentation.di.searchUsersQualifier
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SearchViewModel : BaseSearchViewModel() {

    private val searchScope = getKoin().getOrCreateScope("searchScope", named(searchModuleNamed))
    private val usersScope = getKoin().getOrCreateScope(searchUsersId, named(searchUsersQualifier))

    private val photosDataSource: PhotosDataSource = searchScope.get { parametersOf(viewModelScope) }
    private val factory: PhotosDataSourceFactory = searchScope.get { parametersOf(photosDataSource) }
    private val collectionsDataSource: SearchDataSource<CollectionEntity> = searchScope.get { parametersOf(viewModelScope) }
    private val userDataSource: SearchDataSource<UserEntity> = usersScope.get { parametersOf(viewModelScope) }

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

    fun getUsers(): LiveData<PagedList<UserEntity>> {
        return Transformations.switchMap(photoParamsObservable) {
            userDataSource.query = it.query
            LivePagedListBuilder<Int, UserEntity>(getFactory(userDataSource), config).build()
        }
    }

    fun getRetryAction() {
        userDataSource.retryAction?.invoke()
    }

    val networkStatus: LiveData<Resource.Status> by lazy {
        userDataSource.networkStatus
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