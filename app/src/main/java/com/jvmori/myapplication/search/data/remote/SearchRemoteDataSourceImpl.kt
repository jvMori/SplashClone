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
        if (params.color.isNotEmpty())
            mapOfParams["color"] = params.color
        if (params.orientation.isNotEmpty())
            mapOfParams["orientation"] = params.orientation

        return api.searchPhotos(mapOfParams).mapToEntity(params.page)
    }

    private fun SearchPhotosResponse?.mapToEntity(page: Int): List<PhotoEntity> {
        return this?.photos?.map {
            PhotoEntity(
                it.id,
                it.urls.regular,
                page
            )
        } ?: listOf()
    }

    override suspend fun searchCollections(query: String, page: Int): List<CollectionEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}