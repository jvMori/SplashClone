package com.jvmori.myapplication.data.source

import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.domain.repositories.LocalPhotosDataSource
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource(private var photos : List<PhotoData> = mutableListOf()) :
    LocalPhotosDataSource {

    override suspend fun getPhotos(page: Int, order: String): List<PhotoData> {
        return photos
    }

    override fun getPhotosForCollection(id: Int): Flow<List<PhotoData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun update(data: List<PhotoData>) {
        photos = data.toMutableList()
    }
}