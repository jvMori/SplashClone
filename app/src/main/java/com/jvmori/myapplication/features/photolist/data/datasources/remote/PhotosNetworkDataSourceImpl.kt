package com.jvmori.myapplication.features.photolist.data.datasources.remote

import com.jvmori.myapplication.core.network.Api
import com.jvmori.myapplication.features.photolist.data.models.PhotoData

class PhotosNetworkDataSourceImpl(private val api: Api) :
    PhotosNetworkDataSource {
    override suspend fun getPhotos(page: Int): List<PhotoData> {
        return api.getPhotos(page)
    }
}