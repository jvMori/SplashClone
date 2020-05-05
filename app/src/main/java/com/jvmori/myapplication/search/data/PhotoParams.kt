package com.jvmori.myapplication.search.data

import com.jvmori.myapplication.photoslist.data.remote.Order

enum class Orientation {
    All,
    Landscape,
    Portrait,
    Squarish
}

data class PhotoParams (
    var query : String,
    var page : Int,
    var orientation : String = "",
    var order : Order = Order.latest,
    var color : String = ""
)