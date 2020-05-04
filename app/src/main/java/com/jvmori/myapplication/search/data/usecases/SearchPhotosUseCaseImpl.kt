package com.jvmori.myapplication.search.data.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import com.jvmori.myapplication.search.domain.usecases.SearchPhotosUseCase

class SearchPhotosUseCaseImpl(
    private val repository: SearchRepository
) : SearchPhotosUseCase {
    override suspend fun searchPhotos(params: PhotoParams): Resource<List<PhotoEntity>> {
        return repository.searchPhotos(params)
    }
}