package com.jvmori.myapplication.search.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.domain.usecases.SearchCollectionsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CollectionsDataSource(
    private val scope: CoroutineScope,
    private val useCase: SearchCollectionsUseCase
) : PageKeyedDataSource<Int, CollectionEntity>() {

    var query: String = ""
    var networkStatus: MutableLiveData<Resource.Status> = MutableLiveData()
    var retryAction: (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CollectionEntity>) {
        scope.launch {
            useCase.searchCollections(query, 1).run {
                when (status) {
                    Resource.Status.SUCCESS -> data?.let { callback.onResult(it, null, 2) }
                    Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retryAction = {
                        loadInitial(params, callback)
                    }
                }
                networkStatus.postValue(status)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {
        scope.launch {
            useCase.searchCollections(query, params.key).run {
                when (status) {
                    Resource.Status.SUCCESS -> data?.let { callback.onResult(it, params.key + 1) }
                    Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> retryAction = {
                        loadAfter(params, callback)
                    }
                }
                networkStatus.postValue(status)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}