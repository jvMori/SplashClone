package com.jvmori.myapplication.photoslist.domain.usecases

import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

interface GetPhotosListUseCase {
    suspend fun getPhotos(page : Int = 1, order : String) : Resource<List<PhotoEntity>>
}