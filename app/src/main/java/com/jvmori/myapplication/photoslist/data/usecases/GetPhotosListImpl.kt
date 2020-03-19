package com.jvmori.myapplication.photoslist.data.usecases

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase

class GetPhotosListImpl(private val repository: PhotosRepository) :
    GetPhotosListUseCase {
    override suspend fun getPhotos(page : Int, order : String) : Resource<List<PhotoEntity>> {
        return repository.getPhotos(page, order)
    }
}