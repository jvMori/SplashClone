package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsDataSource
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsDataSourceFactory
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.remote.Resource
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val useCase: GetCollectionsUseCase,
    application: Application
) : AndroidViewModel(application), KoinComponent {

    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setEnablePlaceholders(false)
        .build()

    private val collectionsDataSource = CollectionsDataSource(viewModelScope, useCase)
    private val dataSourceFactory = CollectionsDataSourceFactory(collectionsDataSource)
    val collections by lazy {
        LivePagedListBuilder<Int, CollectionEntity>(dataSourceFactory, config).build()
    }
    val networkState : LiveData<Resource.Status> = dataSourceFactory.networkState
}