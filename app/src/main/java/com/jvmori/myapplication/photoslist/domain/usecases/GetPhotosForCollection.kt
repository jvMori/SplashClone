package com.jvmori.myapplication.photoslist.domain.usecases

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

interface GetPhotosForCollection {
    suspend fun getPhotosForCollection(id: Int): Flow<Resource<List<PhotoEntity>>>
}