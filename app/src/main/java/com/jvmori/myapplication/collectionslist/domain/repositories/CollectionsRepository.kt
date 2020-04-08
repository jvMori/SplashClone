package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    fun getCollections(page : Int) : Flow<List<CollectionEntity>>
    suspend fun getFeaturedCollections(page : Int) : Resource<List<CollectionEntity>>
}