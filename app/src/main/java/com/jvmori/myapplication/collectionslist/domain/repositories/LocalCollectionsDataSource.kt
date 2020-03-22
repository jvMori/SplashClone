package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.common.data.Resource

interface LocalCollectionsDataSource {
    suspend fun getCollections(page: Int): Resource<List<CollectionsData>>
    suspend fun insertCollections(data: List<CollectionsData>)
}