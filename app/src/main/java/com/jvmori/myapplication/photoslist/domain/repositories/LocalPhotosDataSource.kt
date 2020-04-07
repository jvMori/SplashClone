package com.jvmori.myapplication.photoslist.domain.repositories

import com.jvmori.myapplication.photoslist.data.local.PhotoData
import kotlinx.coroutines.flow.Flow

interface LocalPhotosDataSource {
    suspend fun getPhotos(page: Int, order : String) : List<PhotoData>
    fun getPhotosForCollection(id : Int) : Flow<List<PhotoData>>
    suspend fun update(data: List<PhotoData>)
}