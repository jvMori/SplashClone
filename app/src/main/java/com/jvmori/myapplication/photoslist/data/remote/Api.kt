package com.jvmori.myapplication.photoslist.data.remote

import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

enum class Order {latest, popular, oldest}

interface Api {
    @GET("photos/")
    suspend fun getPhotos(
        @Query("page") page : Int = 1,
        @Query("order_by") order : String = Order.latest.toString()
    ) : List<PhotoDataResponse>

    @GET("collections/{id}/photos")
    suspend fun getPhotosForCollection(@Path("id") id : Int) : List<PhotoDataResponse>
}