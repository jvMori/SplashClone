package com.jvmori.myapplication.photoslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import kotlinx.coroutines.CoroutineScope

open class PhotosDataSourceFactory(
   private val photosDataSource: PhotosDataSource
) : DataSource.Factory<Int, PhotoEntity>(){

    val photosLiveData = MutableLiveData<PhotosDataSource>()
    var order : String = ""

    override fun create(): DataSource<Int, PhotoEntity> {
        photosLiveData.postValue(photosDataSource)
        return photosDataSource
    }
}