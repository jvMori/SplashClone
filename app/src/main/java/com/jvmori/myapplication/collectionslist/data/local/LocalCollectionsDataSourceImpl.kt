package com.jvmori.myapplication.collectionslist.data.local

import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.common.data.Resource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class LocalCollectionsDataSourceImpl(private val dao: CollectionsDao) :
    LocalCollectionsDataSource {
    override fun getCollections(page: Int): Flow<List<CollectionsData>> {
       return dao.getCollections(page)
    }

    override suspend fun insertCollections(data: List<CollectionsData>) {
        dao.updateCollection(data)
    }
}