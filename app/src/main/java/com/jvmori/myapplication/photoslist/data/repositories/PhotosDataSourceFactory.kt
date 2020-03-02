package com.jvmori.myapplication.photoslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosList
import kotlinx.coroutines.CoroutineScope

class PhotosDataSourceFactory(
    private var scope: CoroutineScope,
    private val fetchPhotos: GetPhotosList,
    private val order : String
) : DataSource.Factory<Int, PhotoEntity>(){

    val photosLiveData = MutableLiveData<PhotosDataSource>()

    override fun create(): DataSource<Int, PhotoEntity> {
        val photosDataSource =
            PhotosDataSource(
                scope,
                fetchPhotos,
                order
            )
        photosLiveData.postValue(photosDataSource)
        return photosDataSource
    }
}