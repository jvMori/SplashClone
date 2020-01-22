package com.jvmori.myapplication.data.local

import com.jvmori.myapplication.domain.repositories.LocalPhotosDataSource

class LocalPhotosDataSourceImpl(private var photosDao: PhotosDao) : LocalPhotosDataSource<List<PhotoData>> {
    override suspend fun getPhotos(page: Int): List<PhotoData> {
        return photosDao.getPhotos(page)
    }

    override suspend fun update(data: List<PhotoData>) {
        photosDao.updatePhotos(data)
    }
}