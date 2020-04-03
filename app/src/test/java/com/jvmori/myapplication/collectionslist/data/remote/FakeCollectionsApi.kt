package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCollectionsApi(private val data: CollectionsResponse) : CollectionsApi {
    override fun getCollections(page: Int): Flow<List<CollectionsResponse>> {
        return flowOf(listOf(data))
    }
}