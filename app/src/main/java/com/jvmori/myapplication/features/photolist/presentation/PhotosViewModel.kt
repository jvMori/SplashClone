package com.jvmori.myapplication.features.photolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository
import kotlinx.coroutines.launch

class PhotosViewModel (
    private val repository: PhotosRepository
)  : ViewModel() {

    private val _photos = MutableLiveData<Resource<List<PhotoData>>>()
    val photos : LiveData<Resource<List<PhotoData>>> = _photos

   fun fetchPhotos() {
       viewModelScope.launch {
           val data = repository.getPhotos()
           _photos.value = data
       }
   }
}