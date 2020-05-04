package com.jvmori.myapplication.search.data.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.repositories.SearchRepository

class SearchRepositoryImpl : SearchRepository {
    override suspend fun searchPhotos(params: PhotoParams): Resource<List<PhotoEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun searchCollections(query: String, page: Int): Resource<List<CollectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}