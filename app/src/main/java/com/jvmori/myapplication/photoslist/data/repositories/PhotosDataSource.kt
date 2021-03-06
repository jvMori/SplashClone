package com.jvmori.myapplication.photoslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.data.remote.Order
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosListUseCase
import com.jvmori.myapplication.search.data.Orientation
import com.jvmori.myapplication.search.data.PhotoParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

open class PhotosDataSource(
    private var scope: CoroutineScope,
    private val getPhotosListUseCase: GetPhotosListUseCase
) : PageKeyedDataSource<Int, PhotoEntity>() {

    var networkStatus : MutableLiveData<Resource.Status> = MutableLiveData()
    var photoParams : PhotoParams = PhotoParams("", 1)
    var retryAction : (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoEntity>) {
        val currentPage = 1
        photoParams.page = currentPage
        updateState(Resource.Status.LOADING)
        scope.launch {
            val response = getPhotosListUseCase.getPhotos(photoParams)
            response.run {
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        callback.onResult(response.data!!, null, currentPage + 1)
                    }
                    Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retryAction = {
                        loadInitial(params, callback)
                    }
                }
                updateState(response.status)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoEntity>) {
        updateState(Resource.Status.LOADING)
        scope.launch {
            photoParams.page = params.key
            val response = getPhotosListUseCase.getPhotos(photoParams)
            response.run {
                when (response.status) {
                    Resource.Status.SUCCESS -> {
                        callback.onResult(response.data!!, params.key + 1)
                    }
                    Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retryAction = {
                        loadAfter(params, callback)
                    }
                }
                updateState(response.status)
            }
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