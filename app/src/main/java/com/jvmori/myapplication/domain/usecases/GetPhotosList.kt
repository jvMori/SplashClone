package com.jvmori.myapplication.domain.usecases

import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.PhotosRepository

class GetPhotosList(private val repository: PhotosRepository) {
    suspend fun getPhotos(page : Int = 1) : Resource<List<PhotoEntity>> {
        return repository.getPhotos(page)
    }
}