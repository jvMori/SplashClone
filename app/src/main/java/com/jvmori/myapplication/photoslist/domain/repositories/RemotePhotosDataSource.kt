package com.jvmori.myapplication.photoslist.domain.repositories

interface RemotePhotosDataSource<ResponseType> {
    suspend fun getPhotos(page: Int = 1, order : String): ResponseType
}