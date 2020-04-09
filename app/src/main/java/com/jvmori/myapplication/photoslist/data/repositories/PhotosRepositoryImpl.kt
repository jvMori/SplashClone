package com.jvmori.myapplication.photoslist.data.repositories

import com.jvmori.myapplication.common.data.NetworkBoundResource
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.common.data.STALE_DATA_MS
import com.jvmori.myapplication.common.data.fetchData
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import com.jvmori.myapplication.photoslist.domain.repositories.PhotosRepository
import com.jvmori.myapplication.photoslist.domain.repositories.RemotePhotosDataSource
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

data class Parameters(val page: Int = 0, val order: String = "None", var collectionId: Int = 0)

class PhotosRepositoryImpl(
    private val remotePhotosDataSource: RemotePhotosDataSource,
    private val localPhotosDataSource: LocalPhotosDataSource
) : PhotosRepository, NetworkBoundResource<List<PhotoData>, List<PhotoDataResponse>, List<PhotoEntity>, Parameters> {

    override fun getPhotosForCollection(id: Int): Flow<Resource<List<PhotoEntity>>> {
        return fetchData(
            { localPhotosDataSource.getPhotosForCollection(id) },
            { remotePhotosDataSource.getPhotosForCollection(id) },
            { localPhotosDataSource.update(it) },
            { dataMapper(it, Parameters(collectionId = id)) },
            { resultDataMapper(it) }
        )
    }

    override suspend fun getPhotos(page: Int, order: String): Resource<List<PhotoEntity>> {
        return fetchData(Parameters(page, order))
    }

    override suspend fun refreshPhotos(page: Int, order: String): Resource<List<PhotoEntity>> {
        return networkRequest(Parameters(page, order))
    }

    override suspend fun fetchLocalData(params: Parameters): List<PhotoData> {
        return localPhotosDataSource.getPhotos(params.page, params.order)
    }

    override suspend fun fetchNetworkData(params: Parameters): List<PhotoDataResponse> {
        return remotePhotosDataSource.getPhotos(params.page, params.order)
    }

    override fun dataMapper(data: List<PhotoDataResponse>, params: Parameters): List<PhotoData> {
        return data.map {
            PhotoData(
                params.page,
                params.collectionId,
                params.order,
                it.id,
                it.urls
            )
        }
    }

    override suspend fun saveCallResult(data: List<PhotoData>) {
        localPhotosDataSource.update(data)
    }

    override fun resultDataMapper(data: List<PhotoData>): List<PhotoEntity> {
        return data.map {
            PhotoEntity(
                it.id,
                it.urls.small,
                it.page
            )
        }
    }

    override suspend fun refreshNeeded(data: List<PhotoData>): Boolean {
        val timeDiff: Long =
            if (data.isNotEmpty()) abs(data[0].timestamp - System.currentTimeMillis()) else STALE_DATA_MS + 1L
        val isStale = timeDiff > STALE_DATA_MS
        return data.isEmpty() || isStale
    }
}
