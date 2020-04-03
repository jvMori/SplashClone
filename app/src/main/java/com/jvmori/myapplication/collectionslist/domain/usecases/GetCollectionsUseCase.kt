package com.jvmori.myapplication.collectionslist.domain.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.flow.Flow

interface GetCollectionsUseCase {
    suspend fun getCollections(page: Int): Flow<Resource<List<CollectionEntity>>>
}