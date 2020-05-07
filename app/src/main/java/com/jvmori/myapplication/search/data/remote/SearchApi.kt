package com.jvmori.myapplication.search.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchApi {
    @GET("search/photos/")
    suspend fun searchPhotos(@QueryMap params: Map<String, String>): SearchPhotosResponse?

    @GET("search/collections")
    suspend fun searchCollections(@Query("query") query : String, @Query("page") page : Int) : SearchCollectionsResponse?

    @GET("search/users")
    suspend fun searchUsers(@Query("query") query: String, @Query("page")page: Int) : SearchUsersResponse?
}