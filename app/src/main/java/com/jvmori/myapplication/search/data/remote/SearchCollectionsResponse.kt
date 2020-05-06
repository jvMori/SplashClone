package com.jvmori.myapplication.search.data.remote

import com.google.gson.annotations.SerializedName
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse

data class SearchCollectionsResponse(
    @SerializedName("results")
    var collections: List<CollectionsResponse> = listOf()
)

