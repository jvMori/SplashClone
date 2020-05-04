package com.jvmori.myapplication.search.domain.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams

interface SearchPhotosUseCase {
    suspend fun searchPhotos(params : PhotoParams) : Resource<List<PhotoEntity>>
}