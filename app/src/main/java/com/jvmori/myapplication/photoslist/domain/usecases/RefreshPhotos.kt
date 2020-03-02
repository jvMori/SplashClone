package com.jvmori.myapplication.photoslist.domain.usecases

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository

class RefreshPhotos(private  val repository: PhotosRepository){
    suspend fun refresh(page : Int = 1, order : String) : Resource<List<PhotoEntity>> {
        return repository.refreshPhotos(page, order)
    }
}