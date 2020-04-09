package com.jvmori.myapplication.collectionslist.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "collections_table", primaryKeys = ["collection_id", "collection_page"])
data class CollectionsData(
    @ColumnInfo(name = "collection_page")
    var page: Int,

    @ColumnInfo(name = "collection_id")
    var id: Int,

    @ColumnInfo(name = "collection_title")
    var title: String = "",

    @ColumnInfo(name = "collection_total_photos")
    var totalPhotos: Int = 0,

    @ColumnInfo(name = "collection_author_name")
    var authorName: String = "",

    @ColumnInfo(name = "collection_url")
    var smallSizeUrl: String = "",

    @ColumnInfo(name="collection_time")
    override val timestamp : Long = System.currentTimeMillis()

) : ICountTime

interface ICountTime{
    val timestamp : Long
}