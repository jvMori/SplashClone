package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.common.data.remote.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteCollectionsDataSource {
    suspend fun getCollections(page: Int, type : CollectionType): List<CollectionsResponse>
}