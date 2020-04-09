package com.jvmori.myapplication.collectionslist.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse

class FakeCollectionsApi(private val data: CollectionsResponse, private val isError: Boolean = false) : CollectionsApi {
    override suspend fun getCollections(page: Int): List<CollectionsResponse> {
        if (isError) {
            throw NetworkErrorException()
        }
        return listOf(data)
    }
}