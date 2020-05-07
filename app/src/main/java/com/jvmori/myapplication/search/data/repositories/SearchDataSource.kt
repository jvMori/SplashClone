package com.jvmori.myapplication.search.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.domain.usecases.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchDataSource<Result>(
    private val scope: CoroutineScope,
    private val useCase: BaseUseCase<List<Result>>
) : PageKeyedDataSource<Int, Result>() {

    var query: String = ""
    var networkStatus: MutableLiveData<Resource.Status> = MutableLiveData()
    var retryAction: (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {
        networkStatus.postValue(Resource.Status.LOADING)
        scope.launch {
            useCase.fetchData(query, 1).run {
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        networkStatus.postValue(Resource.Status.LOADING)
        scope.launch {
            useCase.fetchData(query, params.key).run {
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }
}