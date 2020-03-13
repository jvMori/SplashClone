package com.jvmori.myapplication.photoslist.data.remote

import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource

class RemotePhotosDataSourceImpl(private var api: Api) :
    RemotePhotosDataSource {
    override suspend fun getPhotos(page: Int, order: String): List<PhotoDataResponse> {
        return api.getPhotos(page, order)
    }
}