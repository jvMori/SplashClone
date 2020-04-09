package com.jvmori.myapplication.data.source

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlinx.coroutines.flow.Flow

class FakeRemoteDataSource(
    private val photos: MutableList<PhotoDataResponse> = mutableListOf()
) : RemotePhotosDataSource {

    override suspend fun getPhotos(page: Int, order: String): List<PhotoDataResponse> {
        return photos
    }

    override fun getPhotosForCollection(id: Int): Flow<Resource<List<PhotoDataResponse>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}