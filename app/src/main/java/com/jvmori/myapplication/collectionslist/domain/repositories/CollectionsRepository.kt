package com.jvmori.myapplication.collectionslist.domain.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource

interface CollectionsRepository {

    suspend fun fetchAndSaveRemoteCollections(page : Int) : Resource<List<CollectionEntity>>
    fun fetchLocalCollections() : DataSource.Factory<Int, CollectionEntity>
    fun shouldUpdate(data : CollectionEntity) : Boolean
    suspend fun getFeaturedCollections(page : Int) : Resource<List<CollectionEntity>>
}