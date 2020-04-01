package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import kotlinx.coroutines.flow.Flow

class RemoteCollectionsDataSourceImpl(private val api: CollectionsApi) :
    RemoteCollectionsDataSource {
    override fun getCollections(page: Int): Flow<List<CollectionsResponse>> {
        return api.getCollections(page)
    }

    override fun getFeaturedCollections(page: Int): Flow<List<CollectionsResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}