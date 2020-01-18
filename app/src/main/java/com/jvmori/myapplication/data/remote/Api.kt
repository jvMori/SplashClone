package com.jvmori.myapplication.data.remote

import com.jvmori.myapplication.data.remote.photodata.PhotoDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("photos/")
    suspend fun getPhotos(@Query("page") page : Int = 1) : List<PhotoDataResponse>
}