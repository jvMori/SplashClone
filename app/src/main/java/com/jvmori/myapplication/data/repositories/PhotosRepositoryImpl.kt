package com.jvmori.myapplication.data.repositories

import com.jvmori.myapplication.data.local.PhotoData
import com.jvmori.myapplication.data.remote.fetchData
import com.jvmori.myapplication.data.remote.networkRequest
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.data.local.PhotosDao
import com.jvmori.myapplication.data.remote.Api
import com.jvmori.myapplication.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.repositories.PhotosRepository

class PhotosRepositoryImpl(
    private val api: Api,
    private val localDataSource: PhotosDao
) : PhotosRepository {
    override suspend fun getPhotos(page: Int): Resource<List<PhotoEntity>> {
        return fetchData(
            { localDataSource.getPhotos(page) },
            { api.getPhotos(page) },
            { refreshNeeded(it) },
            { localDataMapper(it, page) },
            { localDataSource.updatePhotos(it) },
            { uiDataMapper(it, page) }
        )
    }

    override suspend fun refreshPhotos(page: Int): Resource<List<PhotoEntity>> {
        return networkRequest(
            { api.getPhotos(page) },
            { localDataMapper(it, page)},
            { localDataSource.updatePhotos(it) },
            { uiDataMapper(it, page) }
        )
    }

    private fun refreshNeeded(data: List<PhotoData>): Boolean {
        return data.isEmpty()
    }

    private fun localDataMapper(data: List<PhotoDataResponse>, page: Int): List<PhotoData> {
        return data.map {
            PhotoData(
                page,
                it.id,
                it.urls
            )
        }
    }

    private fun uiDataMapper(data: List<PhotoData>, page: Int): List<PhotoEntity> {
        return data.map {
            PhotoEntity(
                it.urls.small,
                page
            )
        }
    }
}
