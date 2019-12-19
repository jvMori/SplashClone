package com.jvmori.myapplication.core.data.network

import com.jvmori.myapplication.features.photolist.data.models.PhotoData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("photos/")
    suspend fun getPhotos(@Query("page") page : Int = 1) : List<PhotoData>
}