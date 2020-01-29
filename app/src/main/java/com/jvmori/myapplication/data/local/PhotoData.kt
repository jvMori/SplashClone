package com.jvmori.myapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.jvmori.myapplication.data.remote.photodata.Urls

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
    var urls: Urls = Urls()
)