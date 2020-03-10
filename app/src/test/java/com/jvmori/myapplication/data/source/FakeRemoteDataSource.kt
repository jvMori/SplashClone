package com.jvmori.myapplication.data.source

import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource

class FakeRemoteDataSource(
    private val photos: MutableList<PhotoDataResponse> = mutableListOf()
) : RemotePhotosDataSource<List<PhotoDataResponse>> {

    override suspend fun getPhotos(page: Int, order: String): List<PhotoDataResponse> {
        return photos
    }
}