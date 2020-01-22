package com.jvmori.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.local.PhotoData
import com.jvmori.myapplication.data.repositories.PhotosDataSource
import com.jvmori.myapplication.data.repositories.PhotosDataSourceFactory
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.PhotosRepository
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import com.jvmori.myapplication.domain.usecases.RefreshPhotos
import kotlinx.coroutines.launch

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

    fun refreshPhotos() {

    }
}