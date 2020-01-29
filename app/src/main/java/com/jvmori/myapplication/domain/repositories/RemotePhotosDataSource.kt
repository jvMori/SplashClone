package com.jvmori.myapplication.domain.repositories

interface RemotePhotosDataSource<ResponseType> {
    suspend fun getPhotos(page: Int = 1, order : String): ResponseType
}