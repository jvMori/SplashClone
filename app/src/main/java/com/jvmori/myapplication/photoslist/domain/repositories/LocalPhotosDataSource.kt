package com.jvmori.myapplication.photoslist.domain.repositories

import com.jvmori.myapplication.photoslist.data.local.PhotoData

interface LocalPhotosDataSource {
    suspend fun getPhotos(page: Int, order : String) : List<PhotoData>
    suspend fun update(data: List<PhotoData>)
}