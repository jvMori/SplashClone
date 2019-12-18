package com.jvmori.myapplication.features.photolist.data.repositories

import com.jvmori.myapplication.core.util.Resource
import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository
import kotlinx.coroutines.flow.Flow

class PhotosRepositoryImpl (
    private val networkDataSource: PhotosNetworkDataSource
) : PhotosRepository {
    override suspend fun getPhotos(page: Int): Resource<List<PhotoData>> {
        return try {
            Resource.success(networkDataSource.getPhotos(page))
        }catch (e : Exception){
            Resource.error(e.message ?: "", null)
        }
    }
}
