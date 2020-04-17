package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.remote.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CollectionsDataSource(
    private val scope: CoroutineScope,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : PageKeyedDataSource<Int, CollectionEntity>() {

    val networkState = MutableLiveData<Resource.Status>()
    var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CollectionEntity>) {
        networkState.postValue(Resource.Status.LOADING)
        scope.launch {
            getCollectionsUseCase.getCollections(1).run {
                this.collect {
                    when (it.status) {
                        is Resource.Status.SUCCESS ->
                            callback.onResult(it.data!!, null, 2)
                        is Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retry = {
                            loadInitial(params, callback)
                        }
                    }
                    networkState.value = it.status
                }
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {
        networkState.postValue(Resource.Status.LOADING)
        scope.launch {
            getCollectionsUseCase.getCollections(params.key).run {
                this.collect {
                    when (it.status) {
                        is Resource.Status.SUCCESS -> callback.onResult(it.data!!, params.key + 1)
                        is Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retry = {
                            loadAfter(params, callback)
                        }
                    }
                    networkState.value = it.status
                }
            }
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}