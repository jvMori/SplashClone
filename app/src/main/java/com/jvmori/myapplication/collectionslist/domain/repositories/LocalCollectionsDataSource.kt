package com.jvmori.myapplication.collectionslist.domain.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import kotlinx.coroutines.flow.Flow

interface LocalCollectionsDataSource {
    fun getCollections(type : CollectionType): DataSource.Factory<Int, CollectionsData>
    suspend fun insertCollections(data: List<CollectionsData>)
    suspend fun insertCollection(data: CollectionsData)
}