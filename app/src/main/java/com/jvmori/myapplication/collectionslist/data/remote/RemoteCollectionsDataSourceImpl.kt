package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.remote.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteCollectionsDataSourceImpl(private val api: CollectionsApi) :
    RemoteCollectionsDataSource {

    override suspend fun getCollections(page: Int, type : CollectionType): List<CollectionsResponse> {
        return when (type){
            CollectionType.DefaultCollection -> api.getCollections(page)
            CollectionType.FeaturedCollection -> api.getFeaturedCollections(page)
        }
    }
}