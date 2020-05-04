package com.jvmori.myapplication.photoslist.domain.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams

interface GetPhotosListUseCase {
    suspend fun getPhotos(params : PhotoParams) : Resource<List<PhotoEntity>>
}