package com.jvmori.myapplication.collectionslist.data.remote

import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionsApi {
    @GET("collections/")
    suspend fun getCollections(@Query("page") page: Int): List<CollectionsResponse>
}