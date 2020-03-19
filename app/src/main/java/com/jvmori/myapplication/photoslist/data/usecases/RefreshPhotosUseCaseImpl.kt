package com.jvmori.myapplication.photoslist.data.usecases

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.usecases.RefreshPhotosUseCase

class RefreshPhotosUseCaseImpl(private  val repository: PhotosRepository) :
    RefreshPhotosUseCase {
    override suspend fun refresh(page : Int, order : String) : Resource<List<PhotoEntity>> {
        return repository.refreshPhotos(page, order)
    }
}