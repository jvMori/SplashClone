package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.repositories.CollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class CollectionsViewModel(
    private val useCase: GetCollectionsUseCase
) : ViewModel(), KoinComponent {

    private val config by inject<PagedList.Config>()

    val collections by lazy {  initializedPagedListBuilder(config).build() }
    val networkInfo by lazy {  }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, CollectionEntity> {
        val dataSourceFactory = object : DataSource.Factory<Int, CollectionEntity>() {
            override fun create(): DataSource<Int, CollectionEntity> {
                return CollectionsDataSource(viewModelScope, useCase)
            }
        }
        return LivePagedListBuilder<Int, CollectionEntity>(dataSourceFactory, config)
    }
}