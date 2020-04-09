package com.jvmori.myapplication.collectionslist.domain.entities

import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity

data class CollectionWithPhotosEntity(
    val collectionEntity: CollectionEntity,
    var photos : List<PhotoEntity> = mutableListOf()
)