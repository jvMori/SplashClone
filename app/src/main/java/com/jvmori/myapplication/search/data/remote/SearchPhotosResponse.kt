package com.jvmori.myapplication.search.data.remote

import com.google.gson.annotations.SerializedName
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse

data class SearchPhotosResponse(
    @SerializedName("results")
    var photos: List<PhotoDataResponse> = listOf()
)