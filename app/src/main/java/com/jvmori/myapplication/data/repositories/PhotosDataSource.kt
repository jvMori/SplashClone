package com.jvmori.myapplication.data.repositories

import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.data.remote.Resource
import com.jvmori.myapplication.domain.entities.PhotoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PhotosDataSource(
    private var scope: CoroutineScope,
    private val fetchPhotos: suspend (Int) -> Resource<List<PhotoEntity>>
) : PageKeyedDataSource<Int, PhotoEntity>() {

    var networkStatus = Resource.loading(null)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoEntity>) {
        val currentPage = 1
        scope.launch {
            val response = fetchPhotos(currentPage)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    callback.onResult(response.data!!, null, currentPage + 1)
                }
                Resource.Status.ERROR -> {
                    networkStatus = Resource.error(response.message ?: "Error while loading data", null)
                }
                Resource.Status.LOADING -> networkStatus = Resource.loading(null)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoEntity>) {
        scope.launch {
            val response = fetchPhotos(params.key)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    callback.onResult(response.data!!, params.key + 1)
                }
                Resource.Status.ERROR -> {
                    networkStatus = Resource.error(response.message ?: "Error while loading data", null)
                }
                Resource.Status.LOADING -> networkStatus = Resource.loading(null)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoEntity>) {

    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}