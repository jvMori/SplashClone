package com.jvmori.myapplication.collectionslist.domain.entities

import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

data class CollectionEntity(
    var id: Int,
    var title: String = "",
    var totalPhotos: Int = 0,
    var authorName: String = "",
    var regularSizeUrl: String = "",
    var photos : List<PhotoEntity> = mutableListOf()
)