package com.jvmori.myapplication.collectionslist.data.local

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LocalCollectionsDataSourceImpl(private val dao: CollectionsDao) :
    LocalCollectionsDataSource {

    override fun getCollections(type : CollectionType): DataSource.Factory<Int, CollectionsData> {
       return when (type){
           CollectionType.DefaultCollection -> dao.getCollections()
           CollectionType.FeaturedCollection -> dao.getFeaturedCollections(type.toString())
       }
    }

    override suspend fun insertCollections(data: List<CollectionsData>) {
        dao.updateCollection(data)
    }

    override suspend fun insertCollection(data: CollectionsData) {
        withContext(Dispatchers.IO){
            dao.insert(data)
        }
    }
}