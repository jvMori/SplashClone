package com.jvmori.myapplication.search.data.repositories

import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.remote.handleError
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.users.domain.UserEntity
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import com.jvmori.myapplication.search.domain.repositories.SearchRepository

class SearchRepositoryImpl(
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchPhotos(params: PhotoParams): Resource<List<PhotoEntity>> {
        return try {
            val result = remoteDataSource.searchPhotos(params)
            Resource.success(result)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun searchCollections(query: String, page: Int): Resource<List<CollectionEntity>> {
        return try {
            val results = remoteDataSource.searchCollections(query, page)
            Resource.success(results)
        } catch (e: java.lang.Exception) {
            handleError(e)
        }
    }

    override suspend fun searchUsers(query: String, page: Int): Resource<List<UserEntity>> {
        return try {
            val results = remoteDataSource.searchUsers(query, page)
            Resource.success(results)
        } catch (e: Exception) {
            handleError(e)
        }
    }
}