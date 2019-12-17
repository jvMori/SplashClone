package com.jvmori.myapplication.features.photolist.data.models


import com.google.gson.annotations.SerializedName

data class CurrentUserCollection(
    @SerializedName("cover_photo")
    var coverPhoto: Any = Any(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("published_at")
    var publishedAt: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    @SerializedName("user")
    var user: User = User()
)