package com.jvmori.myapplication.features.photolist.data.models

import com.google.gson.annotations.SerializedName

data class PhotoData(
    @SerializedName("color")
    var color: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("current_user_collections")
    var currentUserCollections: List<CurrentUserCollection> = listOf(),
    @SerializedName("description")
    var description: String = "",
    @SerializedName("height")
    var height: Int = 0,
    @SerializedName("id")
    var id: String = "",
    @SerializedName("liked_by_user")
    var likedByUser: Boolean = false,
    @SerializedName("likes")
    var likes: Int = 0,
    @SerializedName("links")
    var links: Links = Links(),
    @SerializedName("updated_at")
    var updatedAt: String = "",
    @SerializedName("urls")
    var urls: Urls = Urls(),
    @SerializedName("user")
    var user: User = User(),
    @SerializedName("width")
    var width: Int = 0
)