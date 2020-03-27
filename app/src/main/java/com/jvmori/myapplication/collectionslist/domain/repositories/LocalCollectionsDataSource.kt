package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import kotlinx.coroutines.flow.Flow

interface LocalCollectionsDataSource {
    suspend fun getCollections(page: Int): Flow<List<CollectionsData>>
    suspend fun insertCollections(data: List<CollectionsData>)
}