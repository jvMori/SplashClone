package com.jvmori.myapplication.features.photolist.data.datasources.remote

import com.jvmori.myapplication.core.network.Api
import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSource
import com.jvmori.myapplication.features.photolist.data.models.PhotoResponse

class PhotosNetworkDataSourceImpl(private val api: Api) :
    PhotosNetworkDataSource {
    override suspend fun getPhotos(page: Int): PhotoResponse {
        return api.getPhotos(page)
    }
}