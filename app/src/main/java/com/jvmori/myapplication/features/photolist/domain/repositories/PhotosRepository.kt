package com.jvmori.myapplication.features.photolist.domain.repositories

import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData

interface PhotosRepository {
    suspend fun getPhotos(page : Int = 1) : Resource<List<PhotoData>>
}