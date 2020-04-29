package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.common.data.remote.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsBoundaryCallback(
    private val repository: CollectionsRepository,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<CollectionEntity>() {

    val networkState = MutableLiveData<Resource.Status?>()
    var retryCallback: (suspend () -> Unit)? = null
    lateinit var collectionType : CollectionType

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        scope.launch(Dispatchers.IO) {
            fetchData(1, collectionType)
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: CollectionEntity) {
        super.onItemAtFrontLoaded(itemAtFront)
        scope.launch(Dispatchers.IO) {
            if (repository.shouldUpdate(itemAtFront)) {
                fetchData(itemAtFront.page, collectionType)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: CollectionEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        scope.launch(Dispatchers.IO) {
            fetchData(itemAtEnd.page + 1, collectionType)
        }
    }

    private suspend fun fetchData(page: Int, type : CollectionType) {
        networkState.postValue(Resource.Status.LOADING)
        val result = repository.fetchAndSaveRemoteCollections(page, type)
        retryCallback = when (result.status) {
            is Resource.Status.NETWORK_ERROR, Resource.Status.ERROR -> {
                { fetchData(page, type) }
            }
            else -> null
        }
        networkState.postValue(result.status)
    }
}