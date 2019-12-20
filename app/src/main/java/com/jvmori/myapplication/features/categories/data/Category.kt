package com.jvmori.myapplication.features.categories.data


import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity



sealed class Category {
    data class PhotoCategory(var index : Int, var data : List<PhotoEntity>) : Category()
    data class CollectionCategory(val index : Int) : Category()
}