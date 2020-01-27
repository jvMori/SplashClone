package com.jvmori.myapplication.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.repositories.PhotosDataSource
import com.jvmori.myapplication.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import com.jvmori.myapplication.domain.usecases.RefreshPhotos

class PhotosViewModel(
    photosList: GetPhotosList,
    private val refreshPhotos: RefreshPhotos
) : ViewModel() {

    private val pageSize = 10
    val photos: LiveData<PagedList<PhotoEntity>> by lazy {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        LivePagedListBuilder<Int, PhotoEntity>(photoDataSourceFactory, config).build()
    }

    private var photoDataSourceFactory = PhotosDataSourceFactory(viewModelScope, photosList)

    val networkStatus : LiveData<Resource.Status> = Transformations.switchMap(photoDataSourceFactory.photosLiveData,
        PhotosDataSource::networkStatus)

    fun refreshPhotos() {

    }
}