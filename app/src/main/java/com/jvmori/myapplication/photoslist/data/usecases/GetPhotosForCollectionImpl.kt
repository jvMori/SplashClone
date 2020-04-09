package com.jvmori.myapplication.photoslist.data.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosForCollection
import kotlinx.coroutines.flow.Flow

class GetPhotosForCollectionImpl(private val repository: PhotosRepository) :
    GetPhotosForCollection {
    override suspend fun getPhotosForCollection(id: Int): Flow<Resource<List<PhotoEntity>>> {
        return repository.getPhotosForCollection(id)
    }
}