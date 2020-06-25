package com.jvmori.myapplication.collectionslist.presentation.viewmodels

import androidx.lifecycle.*
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import org.koin.core.KoinComponent

class CollectionsViewModel(
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel(), KoinComponent {

    private val collectionType = MutableLiveData<CollectionType>()
    private val _collectionType: LiveData<CollectionType> = collectionType

    init {
        changeCollectionTo(CollectionType.DefaultCollection)
    }

    val collections = Transformations.switchMap(_collectionType) {
        getCollectionsUseCase.getCollections(it, 10).asLiveData()
    }

    fun changeCollectionTo(type: CollectionType) {
        collectionType.value = type
    }

}