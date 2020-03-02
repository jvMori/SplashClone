package com.jvmori.myapplication.photoslist.data.remote.photodata


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("html")
    var html: String = "",
    @SerializedName("likes")
    var likes: String = "",
    @SerializedName("photos")
    var photos: String = "",
    @SerializedName("portfolio")
    var portfolio: String = "",
    @SerializedName("self")
    var self: String = ""
)