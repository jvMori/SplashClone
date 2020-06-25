package com.jvmori.myapplication.collectionslist.domain.repositories

import androidx.paging.PagingSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData

interface LocalCollectionsDataSource {
    fun getCollections(type: CollectionType): PagingSource<Int, CollectionsData>
    suspend fun insertCollections(data: List<CollectionsData>)
    suspend fun insertCollection(data: CollectionsData)
}