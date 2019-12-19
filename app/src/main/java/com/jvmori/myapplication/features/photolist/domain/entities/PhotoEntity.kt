package com.jvmori.myapplication.features.photolist.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "photos_table", primaryKeys = ["photo_url", "photo_page"])
data class PhotoEntity (
    @ColumnInfo(name = "photo_url")
    var url : String,
    @ColumnInfo(name="photo_page")
    var page : Int
)