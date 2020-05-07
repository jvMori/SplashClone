package com.jvmori.myapplication.search.data.remote

import com.google.gson.annotations.SerializedName
import com.jvmori.myapplication.collectionslist.data.remote.response.User

data class SearchUsersResponse(
    @SerializedName("results")
    var users: List<User> = listOf()
)