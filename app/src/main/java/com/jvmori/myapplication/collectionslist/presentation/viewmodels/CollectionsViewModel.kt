package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsBoundaryCallback
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val repository: CollectionsRepository
) : ViewModel(), KoinComponent {

    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(false)
        .build()

    private val dataSourceFactory = getCollectionsUseCase.getCollections()
    private val boundaryCallback = CollectionsBoundaryCallback(repository, viewModelScope)

    val collections =
        LivePagedListBuilder<Int, CollectionEntity>(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()


    val networkState: LiveData<Resource.Status?> = boundaryCallback.networkState

    fun retry() {
        viewModelScope.launch(Dispatchers.IO) {
            boundaryCallback.retryCallback?.invoke()
        }
    }
}