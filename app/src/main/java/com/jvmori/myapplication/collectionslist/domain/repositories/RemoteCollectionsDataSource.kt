package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteCollectionsDataSource {
    suspend fun getCollections(page: Int): Flow<List<CollectionsResponse>>
    suspend fun getFeaturedCollections(page: Int): Flow<List<CollectionsResponse>>
}