package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCollectionsApi(private val data: CollectionsResponse) : CollectionsApi {
    override suspend fun getCollections(page: Int): List<CollectionsResponse> {
        return listOf(data)
    }
}