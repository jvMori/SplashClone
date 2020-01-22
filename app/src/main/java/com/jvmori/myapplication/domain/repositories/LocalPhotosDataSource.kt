package com.jvmori.myapplication.domain.repositories

interface LocalPhotosDataSource<LocalType> {
    suspend fun getPhotos(page: Int) : LocalType
    suspend fun update(data: LocalType)
}