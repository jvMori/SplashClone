package com.jvmori.myapplication.features.photolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmori.myapplication.features.photolist.data.models.PhotoResponse
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository
import kotlinx.coroutines.launch

class PhotosViewModel (
    private val repository: PhotosRepository
)  : ViewModel() {

    private val _photos = MutableLiveData<PhotoResponse>()
    val photos : LiveData<PhotoResponse> = _photos

   fun fetchPhotos() {
       viewModelScope.launch {
           val data = repository.getPhotos()
           _photos.value = data
       }
   }
}