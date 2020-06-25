package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import kotlinx.coroutines.flow.Flow

class CollectionRepositoryImpl(
    private val localCollectionsDataSource: LocalCollectionsDataSource,
    private val remoteCollectionsDataSource: RemoteCollectionsDataSource
) : CollectionsRepository {

    override fun fetchCollections(collectionType: CollectionType, pageSize: Int): Flow<PagingData<CollectionsData>> =
        Pager(
            config = PagingConfig(pageSize),
            remoteMediator = CollectionRemoteMediator(
                localCollectionsDataSource,
                remoteCollectionsDataSource,
                collectionType
            )
        ) {
            localCollectionsDataSource.getCollections(collectionType)
        }.flow

}