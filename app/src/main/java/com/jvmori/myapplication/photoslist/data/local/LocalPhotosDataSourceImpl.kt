package com.jvmori.myapplication.photoslist.data.local

import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import kotlinx.coroutines.flow.Flow

class LocalPhotosDataSourceImpl(private var photosDao: PhotosDao) :
    LocalPhotosDataSource {
    override suspend fun getPhotos(page: Int, order : String): List<PhotoData> {
        return photosDao.getPhotos(page, order)
    }

    override fun getPhotosForCollection(id: Int): Flow<List<PhotoData>> {
       return photosDao.getPhotosForCollection(id)
    }

    override suspend fun update(data: List<PhotoData>) {
        photosDao.updatePhotos(data)
    }
}