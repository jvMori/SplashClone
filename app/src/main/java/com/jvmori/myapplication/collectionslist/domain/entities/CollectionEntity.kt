package com.jvmori.myapplication.collectionslist.domain.entities

import com.jvmori.myapplication.collectionslist.data.local.ICountTime

data class CollectionEntity(
    var id: Int,
    var title: String = "",
    var totalPhotos: Int = 0,
    var authorName: String = "",
    var smallSizeUrl: String = "",
    override val timestamp: Long = System.currentTimeMillis()
) : ICountTime