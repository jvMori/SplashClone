package com.jvmori.myapplication.collectionslist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections_table")
data class CollectionsData(
    @PrimaryKey
    var page : Int,
    var collectionId: Int = 1,
    var title: String = "",
    var totalPhotos: Int = 0,
    var authorName: String = "",
    var regularSizeUrl: String = ""
)