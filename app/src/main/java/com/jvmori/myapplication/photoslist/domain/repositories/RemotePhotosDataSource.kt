package com.jvmori.myapplication.photoslist.domain.repositories

import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import kotlinx.coroutines.flow.Flow

interface RemotePhotosDataSource {
    suspend fun getPhotos(page: Int = 1, order : String): List<PhotoDataResponse>
    fun getPhotosForCollection(id : Int) : Flow<Resource<List<PhotoDataResponse>>>
}