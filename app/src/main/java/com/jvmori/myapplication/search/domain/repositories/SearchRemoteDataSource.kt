package com.jvmori.myapplication.search.domain.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.users.domain.UserEntity

interface SearchRemoteDataSource {
    suspend fun searchPhotos(params: PhotoParams): List<PhotoEntity>
    suspend fun searchCollections(query: String, page: Int): List<CollectionEntity>
    suspend fun searchUsers(query: String, page: Int): List<UserEntity>
}