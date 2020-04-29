package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.jvmori.myapplication.collectionslist.data.CollectionType
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

    //TODO: inject with koin
    private val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(false)
        .build()

    //TODO: inject with koin
    private val boundaryCallback = CollectionsBoundaryCallback(repository, viewModelScope)

    private val collectionType = MutableLiveData<CollectionType>()
    private val _collectionType: LiveData<CollectionType> = collectionType

    init {
        changeCollectionTo(CollectionType.DefaultCollection)
    }

    val collections = Transformations.switchMap(_collectionType) {
        boundaryCallback.collectionType = it
        val dataSourceFactory = getDataSourceFactory(it)
        LivePagedListBuilder<Int, CollectionEntity>(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    val networkState: LiveData<Resource.Status?> = boundaryCallback.networkState

    fun retry() {
        viewModelScope.launch(Dispatchers.IO) {
            boundaryCallback.retryCallback?.invoke()
        }
    }

    fun changeCollectionTo(type : CollectionType){
        collectionType.value = type
    }

    private fun getDataSourceFactory(type: CollectionType) : DataSource.Factory<Int, CollectionEntity>{
        return getCollectionsUseCase.getCollections(type)
    }
}