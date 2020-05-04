package com.jvmori.myapplication.photoslist.data.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.search.data.PhotoParams

class GetPhotosListImpl(private val repository: PhotosRepository) :
    GetPhotosListUseCase {
    override suspend fun getPhotos(params: PhotoParams) : Resource<List<PhotoEntity>> {
        return repository.getPhotos(params.page, params.order.toString())
    }
}