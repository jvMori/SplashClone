package com.jvmori.myapplication.collectionslist.domain.repositories

import androidx.paging.PagingData
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    fun fetchCollections(collectionType: CollectionType, pageSize: Int): Flow<PagingData<CollectionsData>>
}