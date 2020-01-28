package com.jvmori.myapplication.data.remote

import com.jvmori.myapplication.data.remote.photodata.PhotoDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

enum class Order {latest, popular, oldest}

interface Api {
    @GET("photos/")
    suspend fun getPhotos(
        @Query("page") page : Int = 1,
        @Query("order_by") order : String = Order.latest.toString()
    ) : List<PhotoDataResponse>
}