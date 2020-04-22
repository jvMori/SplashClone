package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsBoundaryCallback
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val repository: CollectionsRepository
) : ViewModel(), KoinComponent {

    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    private val dataSourceFactory = getCollectionsUseCase.getCollections()
    private val boundaryCallback = CollectionsBoundaryCallback(repository, viewModelScope)

    val collections by lazy {
        LivePagedListBuilder<Int, CollectionEntity>(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    //val networkState: LiveData<Resource.Status> = dataSourceFactory.networkState

    fun retry() {
        //collectionsDataSource.retry?.invoke()
    }
}