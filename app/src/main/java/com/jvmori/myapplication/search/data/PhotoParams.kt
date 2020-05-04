package com.jvmori.myapplication.search.data

enum class Orientation {
    All,
    Landscape,
    Portrait,
    Squarish
}

data class PhotoParams (
    var query : String,
    var page : Int,
    var orientation : Orientation,
    var color : String = ""
)