package com.jvmori.myapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.local.PhotoData
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.PhotosRepository
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import com.jvmori.myapplication.domain.usecases.RefreshPhotos
import kotlinx.coroutines.launch

class PhotosViewModel (
    private val photosList: GetPhotosList,
    private val refreshPhotos: RefreshPhotos
)  : ViewModel() {

    private val _photos = MutableLiveData<Resource<List<PhotoEntity>>>()
    val photos : LiveData<Resource<List<PhotoEntity>>> = _photos

   fun fetchPhotos() {
       viewModelScope.launch {
           val data = photosList.getPhotos()
           _photos.value = data
       }
   }
    fun refreshPhotos(){
        viewModelScope.launch {
            val data = refreshPhotos.refresh()
            _photos.value = data
        }
    }
}