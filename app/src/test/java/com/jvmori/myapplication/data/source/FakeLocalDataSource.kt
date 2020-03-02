package com.jvmori.myapplication.data.source

import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource

class FakeLocalDataSource(private var photos : MutableList<PhotoData> = mutableListOf()) :
    LocalPhotosDataSource<List<PhotoData>> {

    override suspend fun getPhotos(page: Int): List<PhotoData> {
        return photos
    }

    override suspend fun update(data: List<PhotoData>) {
        photos = data.toMutableList()
    }

}