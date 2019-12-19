package com.jvmori.myapplication.features.photolist.data.repositories

import com.jvmori.myapplication.core.data.network.fetchData
import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.features.photolist.data.datasources.local.PhotosDao
import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository
import kotlinx.coroutines.flow.Flow

class PhotosRepositoryImpl(
    private val networkDataSource: PhotosNetworkDataSource,
    private val localDataSource: PhotosDao
) : PhotosRepository {
    override suspend fun getPhotos(page: Int): Resource<List<PhotoEntity>> {
        return fetchData(
            { localDataSource.getPhotos(page) },
            { networkDataSource.getPhotos(page) },
            { refreshNeeded(it) },
            { dataMapper(it, page) },
            { localDataSource.insert(it) }
        )
    }

    private fun refreshNeeded(data: List<PhotoEntity>): Boolean {
        return data.isEmpty()
    }

    private fun dataMapper(data: List<PhotoData>, page: Int): List<PhotoEntity> {
        val list = mutableListOf<PhotoEntity>()
        data.forEach {
            list.add(
                PhotoEntity(
                    it.urls.small,
                    page
                )
            )
        }
        return list
    }
}
