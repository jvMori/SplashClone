package com.jvmori.myapplication.collectionslist.data.remote.response


import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("full")
    var full: String = "",
    @SerializedName("raw")
    var raw: String = "",
    @SerializedName("regular")
    var regular: String = "",
    @SerializedName("small")
    var small: String = "",
    @SerializedName("thumb")
    var thumb: String = ""
)