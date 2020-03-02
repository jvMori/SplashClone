package com.jvmori.myapplication.photoslist.data.repositories

import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.common.data.fetchData
import com.jvmori.myapplication.common.data.networkRequest
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource

class PhotosRepositoryImpl(
    private val remotePhotosDataSource: RemotePhotosDataSource<List<PhotoDataResponse>>,
    private val localPhotosDataSource: LocalPhotosDataSource<List<PhotoData>>
) : PhotosRepository {
    override suspend fun getPhotos(page: Int, order : String): Resource<List<PhotoEntity>> {
        return fetchData(
            { localPhotosDataSource.getPhotos(page, order) },
            { remotePhotosDataSource.getPhotos(page, order) },
            { refreshNeeded(it) },
            { mapRequestToLocalData(it, page, order) },
            { localPhotosDataSource.update(it) },
            { mapLocalToResultData(it) }
        )
    }

    override suspend fun refreshPhotos(page: Int, order : String): Resource<List<PhotoEntity>> {
        return networkRequest(
            { remotePhotosDataSource.getPhotos(page, order) },
            { mapRequestToLocalData(it, page, order) },
            { localPhotosDataSource.update(it) },
            { mapLocalToResultData(it) }
        )
    }

    private fun mapRequestToLocalData(response: List<PhotoDataResponse>, page: Int, order : String): List<PhotoData> {
        return response.map {
            PhotoData(
                page,
                order,
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
