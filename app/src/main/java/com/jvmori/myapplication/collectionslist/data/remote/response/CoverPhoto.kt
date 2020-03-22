package com.jvmori.myapplication.collectionslist.data.remote.response


import com.google.gson.annotations.SerializedName

data class CoverPhoto(
    @SerializedName("color")
    var color: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("urls")
    var urls: Urls = Urls()
)