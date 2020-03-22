package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.Resource

interface CollectionsRepository {
    suspend fun getCollections(page : Int) : Resource<List<CollectionEntity>>
    suspend fun getFeaturedCollections(page : Int) : Resource<List<CollectionEntity>>
}