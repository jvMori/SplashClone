package com.jvmori.myapplication.collectionslist.data.remote.response


import com.google.gson.annotations.SerializedName

data class CollectionsResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("cover_photo")
    var coverPhoto: CoverPhoto = CoverPhoto(),
    @SerializedName("description")
    var description: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("total_photos")
    var totalPhotos: Int = 0,
    @SerializedName("user")
    var user: User = User()
)