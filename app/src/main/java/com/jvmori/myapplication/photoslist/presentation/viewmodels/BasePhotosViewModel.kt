package com.jvmori.myapplication.photoslist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

abstract class BasePhotosViewModel : ViewModel(){
    abstract var photos: LiveData<PagedList<PhotoEntity>>?
    abstract var networkStatus: LiveData<Resource.Status>?
    abstract fun fetchPhotos(id: Int?)
    abstract fun retryAction()
}