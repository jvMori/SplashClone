package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.common.data.handleError
import com.jvmori.myapplication.photoslist.domain.usecases.GetPhotosForCollection
import com.paginate.Paginate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val useCase: GetCollectionsUseCase,
    private val getPhotosUseCase: GetPhotosForCollection
) : ViewModel(), KoinComponent {

    private var currentPage = 1
    private val totalPages = 10
    private var loadingInProgress = false
    private val _collections = MutableLiveData<Resource<List<CollectionEntity>>>()
    val collections: LiveData<Resource<List<CollectionEntity>>> = _collections

    fun setupRecyclerView(recyclerView: RecyclerView) {
        Paginate.with(recyclerView, callbacks)
            .setLoadingTriggerThreshold(2)
            .setLoadingListItemSpanSizeLookup { 1 }
            .build()
    }

    fun fetchCollections(page: Int) {
        viewModelScope.launch {
            useCase.getCollections(page)
                .onStart { loadingInProgress = true }
                .catch { exception -> _collections.value = handleError(exception) }
                .flatMapConcat { collections ->
                    flow {
                        collections.map { collectionEntity ->
                            getPhotosUseCase.getPhotosForCollection(collectionEntity.id).distinctUntilChanged()
                                .collect { photos ->
                                    collectionEntity.photos = photos
                                }
                        }
                        emit(collections)
                    }
                }
                .collect {
                    loadingInProgress = false
                    _collections.value = Resource.success(it)
                }
        }
    }

    private val callbacks: Paginate.Callbacks = object : Paginate.Callbacks {
        override fun onLoadMore() {
            loadingInProgress = true
            fetchCollections(currentPage)
            currentPage++
        }

        override fun isLoading(): Boolean {
            return loadingInProgress
        }

        override fun hasLoadedAllItems(): Boolean {
            return currentPage == totalPages
        }
    }
}