package com.jvmori.myapplication.search.domain.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource

interface SearchCollectionsUseCase {
    suspend fun searchCollections(query : String, page : Int) : Resource<List<CollectionEntity>>
}