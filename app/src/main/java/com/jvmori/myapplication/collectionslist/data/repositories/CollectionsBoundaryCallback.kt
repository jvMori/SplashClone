package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsBoundaryCallback(
    private val repository: CollectionsRepository,
    private val scope: CoroutineScope
) : PagedList.BoundaryCallback<CollectionEntity>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        scope.launch(Dispatchers.IO) {
            //TODO:  handle errors and network status
            repository.fetchAndSaveRemoteCollections(1)
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: CollectionEntity) {
        super.onItemAtFrontLoaded(itemAtFront)
        scope.launch(Dispatchers.IO) {
            if (repository.shouldUpdate(itemAtFront)) {
                //TODO: handle errors and network status
                repository.fetchAndSaveRemoteCollections(itemAtFront.page)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: CollectionEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        scope.launch(Dispatchers.IO) {
            //TODO: handle errors and network status
            repository.fetchAndSaveRemoteCollections(itemAtEnd.page + 1)
        }
    }
}