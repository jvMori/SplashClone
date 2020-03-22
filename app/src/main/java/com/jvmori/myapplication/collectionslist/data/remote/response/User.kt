package com.jvmori.myapplication.collectionslist.data.remote.response


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("bio")
    var bio: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("links")
    var links: Links = Links(),
    @SerializedName("location")
    var location: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("portfolio_url")
    var portfolioUrl: String = "",
    @SerializedName("profile_image")
    var profileImage: ProfileImage = ProfileImage(),
    @SerializedName("total_collections")
    var totalCollections: Int = 0,
    @SerializedName("total_likes")
    var totalLikes: Int = 0,
    @SerializedName("total_photos")
    var totalPhotos: Int = 0,
    @SerializedName("updated_at")
    var updatedAt: String = "",
    @SerializedName("username")
    var username: String = ""
)