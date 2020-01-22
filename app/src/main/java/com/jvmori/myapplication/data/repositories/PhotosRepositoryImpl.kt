package com.jvmori.myapplication.data.repositories

import com.jvmori.myapplication.data.local.PhotoData
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.remote.fetchData
import com.jvmori.myapplication.data.remote.networkRequest
import com.jvmori.myapplication.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.domain.repositories.PhotosRepository
import com.jvmori.myapplication.domain.repositories.RemotePhotosDataSource

class PhotosRepositoryImpl(
    private val remotePhotosDataSource: RemotePhotosDataSource<List<PhotoDataResponse>>,
    private val localPhotosDataSource: LocalPhotosDataSource<List<PhotoData>>
) : PhotosRepository {
    override suspend fun getPhotos(page: Int): Resource<List<PhotoEntity>> {
        return fetchData(
            { localPhotosDataSource.getPhotos(page) },
            { remotePhotosDataSource.getPhotos(page) },
            { refreshNeeded(it) },
            { mapRequestToLocalData(it, page) },
            { localPhotosDataSource.update(it) },
            { mapLocalToResultData(it) }
        )
    }

    override suspend fun refreshPhotos(page: Int): Resource<List<PhotoEntity>> {
        return networkRequest(
            { remotePhotosDataSource.getPhotos(page) },
            { mapRequestToLocalData(it, page) },
            { localPhotosDataSource.update(it) },
            { mapLocalToResultData(it) }
        )
    }

    private fun mapRequestToLocalData(response: List<PhotoDataResponse>, page: Int): List<PhotoData> {
        return response.map {
            PhotoData(
                page,
                it.id,
                it.urls
            )
        }
    }

    private fun mapLocalToResultData(local: List<PhotoData>): List<PhotoEntity> {
        return local.map {
            PhotoEntity(
                it.id,
                it.urls.small,
                it.page
            )
        }
    }

    private fun refreshNeeded(data: List<PhotoData>): Boolean {
        return data.isEmpty()
    }

}
