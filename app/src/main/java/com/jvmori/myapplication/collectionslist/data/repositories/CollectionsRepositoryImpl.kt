package com.jvmori.myapplication.collectionslist.data.repositories

import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.remote.fetchData
import kotlinx.coroutines.flow.Flow

class CollectionsRepositoryImpl(
    private val localCollectionsDataSource: LocalCollectionsDataSource,
    private val remoteCollectionsDataSource: RemoteCollectionsDataSource
) : CollectionsRepository {
    override fun getCollections(page: Int): Flow<Resource<List<CollectionEntity>>> {
        return fetchData(
            { localCollectionsDataSource.getCollections(page) },
            { remoteCollectionsDataSource.getCollections(page) },
            { localCollectionsDataSource.insertCollections(it) },
            { networkToLocalMapper(it, page) },
            { localToResultMapper(it) }
        )
    }

    override suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun networkToLocalMapper(networkData: List<CollectionsResponse>, page: Int): List<CollectionsData> {
        return networkData.map {
            CollectionsData(
                page,
                it.id,
                it.title,
                it.totalPhotos,
                it.user.name,
                it.coverPhoto.urls.small
            )
        }
    }

    private fun localToResultMapper(localData: List<CollectionsData>): List<CollectionEntity> {
        return localData.map {
            CollectionEntity(
                it.id,
                it.title,
                it.totalPhotos,
                it.authorName,
                it.smallSizeUrl
            )
        }
    }
}