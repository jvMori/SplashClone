package com.jvmori.myapplication.features.categories.data

import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity

data class Category (
    var index : Int,
    var name : String,
    val items : List<PhotoEntity>?
)