package com.jvmori.myapplication.collectionslist.domain.repositories

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.common.data.Resource

interface RemoteCollectionsDataSource {
    suspend fun getCollections(page: Int): Resource<List<CollectionsResponse>>
    suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionsResponse>>
}