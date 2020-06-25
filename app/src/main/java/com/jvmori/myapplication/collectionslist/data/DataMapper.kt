package com.jvmori.myapplication.collectionslist.data

import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity

fun CollectionsResponse.networkToLocalMapper(page: Int, collectionType: CollectionType): CollectionsData {
    return CollectionsData(
        page,
        this.id,
        this.title,
        this.totalPhotos,
        this.user.name,
        this.coverPhoto.urls.small,
        collectionType.toString()
    )
}

fun CollectionsData.localToEntity(): CollectionEntity = CollectionEntity(
    this.id,
    this.title,
    this.totalPhotos,
    this.authorName,
    this.smallSizeUrl,
    this.page
)