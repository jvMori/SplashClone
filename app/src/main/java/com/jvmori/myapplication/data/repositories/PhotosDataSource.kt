package com.jvmori.myapplication.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity
import com.jvmori.myapplication.domain.usecases.GetPhotosList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PhotosDataSource(
    private var scope: CoroutineScope,
    private val fetchPhotos: GetPhotosList
) : PageKeyedDataSource<Int, PhotoEntity>() {

    var networkStatus : MutableLiveData<Resource.Status> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoEntity>) {
        val currentPage = 1
        updateState(Resource.Status.LOADING)
        scope.launch {
            val response = fetchPhotos.getPhotos(currentPage)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    callback.onResult(response.data!!, null, currentPage + 1)
                }
            }
            updateState(response.status)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoEntity>) {
        updateState(Resource.Status.LOADING)
        scope.launch {
            val response = fetchPhotos.getPhotos(params.key)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    callback.onResult(response.data!!, params.key + 1)
                }
            }
            updateState(response.status)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoEntity>) {

    }

    private fun updateState(status: Resource.Status?) {
        this.networkStatus.postValue(status)
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}