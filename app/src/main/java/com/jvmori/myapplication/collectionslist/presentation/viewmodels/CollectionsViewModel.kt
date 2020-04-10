package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.util.isInternetConnection
import com.paginate.Paginate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val useCase: GetCollectionsUseCase,
    application: Application
) : AndroidViewModel(application), KoinComponent {

    private var currentPage = 1
    private val totalPages = 100
    private var isError = false
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
                .distinctUntilChanged()
                .collect {
                    loadingInProgress = false
                    _collections.value = it
                    isError = it.status == Resource.Status.NETWORK_ERROR || it.status == Resource.Status.ERROR
                }
        }
    }

    private val callbacks: Paginate.Callbacks = object : Paginate.Callbacks {
        override fun onLoadMore() {
            if (isInternetConnection(getApplication()))
                isError = false
            if (!isError) {
                loadingInProgress = true
                fetchCollections(currentPage)
                currentPage++
            }
        }

        override fun isLoading(): Boolean {
            return loadingInProgress
        }

        override fun hasLoadedAllItems(): Boolean {
            return currentPage == totalPages
        }
    }
}