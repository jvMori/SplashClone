package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionsApi {
    @GET("collections/")
    fun getCollections(@Query("page") page: Int) : Flow<List<CollectionsResponse>>
}