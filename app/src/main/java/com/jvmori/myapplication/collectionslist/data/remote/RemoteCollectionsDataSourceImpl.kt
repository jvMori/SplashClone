package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.remote.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteCollectionsDataSourceImpl(private val api: CollectionsApi) :
    RemoteCollectionsDataSource {
    override suspend fun getCollections(page: Int): List<CollectionsResponse> {
        return api.getCollections(page)
    }

    override fun getFeaturedCollections(page: Int): Flow<List<CollectionsResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}