package com.jvmori.myapplication.search.data.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.search.domain.repositories.SearchRepository
import com.jvmori.myapplication.search.domain.usecases.SearchCollectionsUseCase

class SearchCollectionsUseCaseImpl(
    private val repository: SearchRepository
) : SearchCollectionsUseCase {
    override suspend fun searchCollections(query: String, page: Int): Resource<List<CollectionEntity>> {
        return repository.searchCollections(query, page)
    }
}