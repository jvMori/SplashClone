package com.jvmori.myapplication.collectionslist.domain.usecases

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import kotlinx.coroutines.flow.Flow

interface GetCollectionsUseCase {
    fun getCollections(page: Int): Flow<List<CollectionEntity>>
}