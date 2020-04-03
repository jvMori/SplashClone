package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CollectionsDataSource(
    private var scope: CoroutineScope,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : PageKeyedDataSource<Int, CollectionEntity>() {

    var networkStatus: MutableLiveData<Resource.Status> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CollectionEntity>) {
        scope.launch {
            getCollectionsUseCase.getCollections(1).run {
                this.collect {
                    when (it.status) {
                        is Resource.Status.SUCCESS -> callback.onResult(it.data!!, null, 2)
                    }
                    networkStatus.value = it.status
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {
        scope.launch {
            getCollectionsUseCase.getCollections(params.key).run {
                this.collect {
                    when (it.status) {
                        is Resource.Status.SUCCESS -> callback.onResult(it.data!!, params.key + 1)
                    }
                    networkStatus.value = it.status
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionEntity>) {

    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}