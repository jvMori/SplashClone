package com.jvmori.myapplication.photoslist.data.remote

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.common.data.handleError
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemotePhotosDataSourceImpl(private var api: Api) :
    RemotePhotosDataSource {
    override suspend fun getPhotos(page: Int, order: String): List<PhotoDataResponse> {
        return api.getPhotos(page, order)
    }

    override fun getPhotosForCollection(id: Int): Flow<Resource<List<PhotoDataResponse>>> {
        return flow {
            try {
                emit(Resource.success(api.getPhotosForCollection(id)))
            } catch (e: Exception) {
                emit(handleError(e))
            }
        }
    }
}