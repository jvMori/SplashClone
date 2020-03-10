package com.jvmori.myapplication.photoslist.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.jvmori.myapplication.photoslist.data.remote.photodata.Urls
import java.sql.Timestamp

@Entity(tableName = "photos_table", primaryKeys = ["photo_id", "photo_page"])
data class PhotoData(
    @ColumnInfo(name = "photo_page")
    var page: Int,
    @ColumnInfo(name= "photo_order")
    var order : String,
    @ColumnInfo(name = "photo_id")
    var id: String = "",
    @Embedded
    @SerializedName("urls")
    var urls: Urls = Urls(),
    val timestamp: Long = System.currentTimeMillis()
)