package com.jvmori.myapplication.collectionslist.data.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.usecases.GetCollectionsUseCase
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCaseImpl(private val repository: CollectionsRepository) :
    GetCollectionsUseCase {
    override suspend fun getCollections(page: Int): Flow<Resource<List<CollectionEntity>>> {
        return repository.getCollections(page)
    }
}