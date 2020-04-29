package com.jvmori.myapplication.collectionslist.data

sealed class CollectionType {
    object DefaultCollection : CollectionType()
    object FeaturedCollection : CollectionType()
}