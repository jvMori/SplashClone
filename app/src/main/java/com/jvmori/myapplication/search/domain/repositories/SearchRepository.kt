package com.jvmori.myapplication.search.domain.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.entities.UserEntity

interface SearchRepository {
    suspend fun searchPhotos(params: PhotoParams): Resource<List<PhotoEntity>>
    suspend fun searchCollections(query: String, page: Int): Resource<List<CollectionEntity>>
    suspend fun searchUsers(query: String, page: Int): Resource<List<UserEntity>>
}