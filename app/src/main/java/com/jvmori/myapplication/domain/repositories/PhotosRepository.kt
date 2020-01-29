package com.jvmori.myapplication.domain.repositories

import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.local.PhotoData
import com.jvmori.myapplication.domain.entities.PhotoEntity

interface PhotosRepository {
    suspend fun getPhotos(page : Int = 1, order : String) : Resource<List<PhotoEntity>>
    suspend fun refreshPhotos(page : Int = 1, order: String) : Resource<List<PhotoEntity>>
}