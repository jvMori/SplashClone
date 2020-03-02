package com.jvmori.myapplication.photoslist.domain.repositories

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

interface PhotosRepository {
    suspend fun getPhotos(page : Int = 1, order : String) : Resource<List<PhotoEntity>>
    suspend fun refreshPhotos(page : Int = 1, order: String) : Resource<List<PhotoEntity>>
}