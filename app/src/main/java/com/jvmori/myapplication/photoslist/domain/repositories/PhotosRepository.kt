package com.jvmori.myapplication.photoslist.domain.repositories

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(page : Int = 1, order : String) : Resource<List<PhotoEntity>>
    suspend fun refreshPhotos(page : Int = 1, order: String) : Resource<List<PhotoEntity>>
    suspend fun getPhotosForCollection(id : Int) : Resource<List<PhotoEntity>>?
}