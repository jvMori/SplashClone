package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.Resource
import com.paginate.Paginate
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val useCase: GetCollectionsUseCase
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
            useCase.getCollections(page).collect {
                loadingInProgress = when (it.status) {
                    is Resource.Status.LOADING -> true
                    else -> false
                }
                _collections.value = it
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