package com.jvmori.myapplication.photoslist.data.remote.photodata

import com.google.gson.annotations.SerializedName

data class PhotoDataResponse(
    @SerializedName("color")
    var color: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("links")
    var links: Links = Links(),
    @SerializedName("urls")
    var urls: Urls = Urls(),
    @SerializedName("user")
    var user: User = User()
)