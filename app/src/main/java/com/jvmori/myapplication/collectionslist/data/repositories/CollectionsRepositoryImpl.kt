package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRepository
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.remote.Resource
import com.jvmori.myapplication.common.data.remote.handleError
import com.jvmori.myapplication.common.data.remote.refreshNeeded

class CollectionsRepositoryImpl(
    private val localCollectionsDataSource: LocalCollectionsDataSource,
    private val remoteCollectionsDataSource: RemoteCollectionsDataSource
) : CollectionsRepository {

    override suspend fun fetchAndSaveRemoteCollections(page: Int): Resource<List<CollectionEntity>> {
        return try {
            val local = remoteCollectionsDataSource.getCollections(page).map {
                networkToLocalMapper(it, page)
            }
            localCollectionsDataSource.insertCollections(local)
            val result = local.map {
                localToResultMapper(it)
            }
            Resource.success(result)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override fun shouldUpdate(data: CollectionEntity): Boolean {
       return refreshNeeded(data)
    }

    override fun fetchLocalCollections(): DataSource.Factory<Int, CollectionEntity> {
        return localCollectionsDataSource.getCollections().map {
            localToResultMapper(it)
        }
    }

    override suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun networkToLocalMapper(networkData: CollectionsResponse, page: Int): CollectionsData {
        return CollectionsData(
            page,
            networkData.id,
            networkData.title,
            networkData.totalPhotos,
            networkData.user.name,
            networkData.coverPhoto.urls.small
        )

    }

    private fun localToResultMapper(localData: CollectionsData): CollectionEntity {
        return CollectionEntity(
            localData.id,
            localData.title,
            localData.totalPhotos,
            localData.authorName,
            localData.smallSizeUrl,
            localData.page
        )
    }
}