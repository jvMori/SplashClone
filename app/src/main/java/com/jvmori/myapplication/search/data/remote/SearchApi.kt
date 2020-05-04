package com.jvmori.myapplication.search.data.remote

import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchApi {
    @GET("search/photos/")
    suspend fun searchPhotos(@QueryMap params : Map<String, String>) : List<PhotoEntity>
}