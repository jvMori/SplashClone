package com.jvmori.myapplication.photoslist.domain.usecases

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository

class GetPhotosList(private val repository: PhotosRepository) {
    suspend fun getPhotos(page : Int = 1, order : String) : Resource<List<PhotoEntity>> {
        return repository.getPhotos(page, order)
    }
}