package com.jvmori.myapplication.photoslist.data.local

import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource

class LocalPhotosDataSourceImpl(private var photosDao: PhotosDao) :
    LocalPhotosDataSource<List<PhotoData>> {
    override suspend fun getPhotos(page: Int, order : String): List<PhotoData> {
        return photosDao.getPhotos(page, order)
    }

    override suspend fun update(data: List<PhotoData>) {
        photosDao.updatePhotos(data)
    }
}