package com.jvmori.myapplication.photoslist.data.repositories

import com.jvmori.myapplication.common.data.*
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlin.math.abs

data class Parameters(val page : Int, val order : String)

class PhotosRepositoryImpl(
    private val remotePhotosDataSource: RemotePhotosDataSource<List<PhotoDataResponse>>,
    private val localPhotosDataSource: LocalPhotosDataSource<List<PhotoData>>
) : PhotosRepository, NetworkBoundResource<List<PhotoData>, List<PhotoDataResponse>, List<PhotoEntity>, Parameters>{

    override suspend fun getPhotos(page: Int, order : String): Resource<List<PhotoEntity>> {
        return fetchData(Parameters(page, order))
    }

    override suspend fun refreshPhotos(page: Int, order : String): Resource<List<PhotoEntity>> {
        return networkRequest(Parameters(page, order))
    }

    override suspend fun fetchLocalData(params: Parameters): List<PhotoData> {
       return localPhotosDataSource.getPhotos(params.page, params.order)
    }

    override suspend fun fetchNetworkData(params: Parameters): List<PhotoDataResponse> {
        return remotePhotosDataSource.getPhotos(params.page, params.order)
    }

    override suspend fun dataMapper(data: List<PhotoDataResponse>, params: Parameters): List<PhotoData> {
        return data.map {
            PhotoData(
                params.page,
                params.order,
                it.id,
                it.urls
            )
        }
    }

    override suspend fun saveCallResult(data: List<PhotoData>) {
        localPhotosDataSource.update(data)
    }

    override suspend fun resultDataMapper(data: List<PhotoData>): List<PhotoEntity> {
        return data.map {
            PhotoEntity(
                it.id,
                it.urls.small,
                it.page
            )
        }
    }

    override suspend fun refreshNeeded(data: List<PhotoData>): Boolean {
        val timeDiff : Long = if (data.isNotEmpty()) abs(data[0].timestamp - System.currentTimeMillis()) else STALE_DATA_MS + 1L
        val isStale = timeDiff > STALE_DATA_MS
        return data.isEmpty() || isStale
    }
}
