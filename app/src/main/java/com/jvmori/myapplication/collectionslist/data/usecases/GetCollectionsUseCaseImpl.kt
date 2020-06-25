package com.jvmori.myapplication.collectionslist.data.usecases

import androidx.paging.PagingData
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCaseImpl(private val repository: CollectionsRepository) :
    GetCollectionsUseCase {
    override fun getCollections(type: CollectionType, pageSize: Int): Flow<PagingData<CollectionsData>> {
        return repository.fetchCollections(type, pageSize)
    }
}