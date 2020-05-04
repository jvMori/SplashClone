package com.jvmori.myapplication.photoslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import kotlinx.coroutines.CoroutineScope

class PhotosDataSourceFactory(
    private val photosDataSource: PageKeyedDataSource<Int, PhotoEntity>
) : DataSource.Factory<Int, PhotoEntity>(){

    val photosLiveData = MutableLiveData<PhotosDataSource>()

    fun setOrder(order : Order) {
        (photosDataSource as PhotosDataSource).photoParams.order = order
    }

    override fun create(): DataSource<Int, PhotoEntity> {
        photosLiveData.postValue(photosDataSource as PhotosDataSource)
        return photosDataSource
    }
}