package com.jvmori.myapplication.collectionslist.data.local

import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsLocalDataSource
import com.jvmori.myapplication.common.data.Resource
import java.lang.Exception

class CollectionsLocalDataSourceImpl(val dao: CollectionsDao) :
    CollectionsLocalDataSource {
    override suspend fun getCollections(page: Int): Resource<List<CollectionsData>> {
        return try {
            val data = dao.getCollections(page)
            Resource.success(data)
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: "database error", null)
        }
    }

    override suspend fun insertCollections(data: List<CollectionsData>) {
        dao.updateCollection(data)
    }
}