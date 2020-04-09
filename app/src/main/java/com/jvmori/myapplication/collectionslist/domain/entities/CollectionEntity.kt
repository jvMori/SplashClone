package com.jvmori.myapplication.collectionslist.domain.entities

data class CollectionEntity(
    var id: Int,
    var title: String = "",
    var totalPhotos: Int = 0,
    var authorName: String = "",
    var smallSizeUrl: String = ""
)