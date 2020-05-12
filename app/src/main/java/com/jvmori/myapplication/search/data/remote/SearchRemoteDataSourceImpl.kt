package com.jvmori.myapplication.search.data.remote

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.users.domain.UserEntity
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

    override suspend fun searchCollections(query: String, page: Int): List<CollectionEntity> {
        return api.searchCollections(query, page).mapToEntity(page)
    }

    override suspend fun searchUsers(query: String, page: Int): List<UserEntity> {
        return api.searchUsers(query, page).mapToEntity()
    }

    fun SearchUsersResponse?.mapToEntity(): List<UserEntity> {
        return this?.users?.map {
            UserEntity(
                it.id,
                it.name,
                it.username,
                it.profileImage.small,
                it.totalLikes
            )
        } ?: listOf()
    }

    fun SearchPhotosResponse?.mapToEntity(page: Int): List<PhotoEntity> {
        return this?.photos?.map {
            PhotoEntity(
                it.id,
                it.urls.regular,
                page
            )
        } ?: listOf()
    }

    fun SearchCollectionsResponse?.mapToEntity(page: Int): List<CollectionEntity> {
        return this?.collections?.map {
            CollectionEntity(
                it.id,
                it.title,
                it.totalPhotos,
                it.user.name,
                it.coverPhoto.urls.small,
                page
            )
        } ?: listOf()
    }
}