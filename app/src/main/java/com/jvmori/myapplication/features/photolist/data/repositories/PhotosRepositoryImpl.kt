package com.jvmori.myapplication.features.photolist.data.repositories

import com.jvmori.myapplication.features.photolist.data.datasources.remote.PhotosNetworkDataSource
import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import com.jvmori.myapplication.features.photolist.domain.repositories.PhotosRepository

class PhotosRepositoryImpl (
    private val networkDataSource: PhotosNetworkDataSource
) : PhotosRepository {
    override suspend fun getPhotos(page: Int): List<PhotoData> {
        //TODO: handle errors
        return networkDataSource.getPhotos(page)
    }
}