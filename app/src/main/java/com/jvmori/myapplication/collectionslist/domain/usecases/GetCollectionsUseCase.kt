package com.jvmori.myapplication.collectionslist.domain.usecases

import androidx.paging.PagingData
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import kotlinx.coroutines.flow.Flow

interface GetCollectionsUseCase {
    fun getCollections(type: CollectionType, pageSize: Int): Flow<PagingData<CollectionsData>>
}