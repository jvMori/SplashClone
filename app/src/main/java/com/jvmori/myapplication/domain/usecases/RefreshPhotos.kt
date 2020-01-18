package com.jvmori.myapplication.domain.usecases

import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.PhotosRepository

class RefreshPhotos(private  val repository: PhotosRepository){
    suspend fun refresh(page : Int = 1) : Resource<List<PhotoEntity>>{
        return repository.refreshPhotos(page)
    }
}