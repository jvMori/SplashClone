package com.jvmori.myapplication.photoslist.domain.repositories

interface LocalPhotosDataSource<LocalType> {
    suspend fun getPhotos(page: Int, order : String) : LocalType
    suspend fun update(data: LocalType)
}