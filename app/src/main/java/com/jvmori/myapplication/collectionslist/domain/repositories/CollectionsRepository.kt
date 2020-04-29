package com.jvmori.myapplication.collectionslist.domain.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource

interface CollectionsRepository {
    suspend fun fetchAndSaveRemoteCollections(page : Int, type : CollectionType) : Resource<List<CollectionEntity>>
    fun fetchLocalCollections(type : CollectionType) : DataSource.Factory<Int, CollectionEntity>
    fun shouldUpdate(data : CollectionEntity) : Boolean
}