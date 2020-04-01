package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import kotlinx.coroutines.flow.Flow

interface RemoteCollectionsDataSource {
    fun getCollections(page: Int): Flow<List<CollectionsResponse>>
    fun getFeaturedCollections(page: Int): Flow<List<CollectionsResponse>>
}