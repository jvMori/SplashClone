package com.jvmori.myapplication.data.remote

import com.jvmori.myapplication.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.domain.repositories.RemotePhotosDataSource

class RemotePhotosDataSourceImpl(private var api: Api) : RemotePhotosDataSource<List<PhotoDataResponse>> {
    override suspend fun getPhotos(page: Int): List<PhotoDataResponse> {
        return api.getPhotos(page)
    }
}