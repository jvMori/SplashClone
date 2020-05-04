package com.jvmori.myapplication.search.data.remote

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource

class SearchRemoteDataSourceImpl(val api: SearchApi) :
    SearchRemoteDataSource {
    override suspend fun searchPhotos(params: PhotoParams): List<PhotoEntity> {
        val mapOfParams = mutableMapOf<String, String>()

        mapOfParams["query"] = params.query
        mapOfParams["page"] = params.page.toString()
        mapOfParams["color"] = params.color
        mapOfParams["orientation"] = params.orientation.toString()

        return api.searchPhotos(mapOfParams)
    }

    override suspend fun searchCollections(query: String, page: Int): List<CollectionEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}