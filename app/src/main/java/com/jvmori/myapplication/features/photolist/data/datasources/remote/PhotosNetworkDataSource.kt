package com.jvmori.myapplication.features.photolist.data.datasources.remote

import com.jvmori.myapplication.features.photolist.data.models.PhotoData

interface PhotosNetworkDataSource {
    suspend fun getPhotos(page : Int = 1) : List<PhotoData>
}