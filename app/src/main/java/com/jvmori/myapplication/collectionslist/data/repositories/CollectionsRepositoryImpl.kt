package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.DataSource
import com.jvmori.myapplication.collectionslist.data.CollectionType
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

    override suspend fun fetchAndSaveRemoteCollections(page: Int, type : CollectionType): Resource<List<CollectionEntity>> {
        return try {
            val local = remoteCollectionsDataSource.getCollections(page, type).map {
                networkToLocalMapper(it, page, type)
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

    override fun fetchLocalCollections(type : CollectionType): DataSource.Factory<Int, CollectionEntity> {
        return localCollectionsDataSource.getCollections(type).map {
            localToResultMapper(it)
        }
    }

    private fun networkToLocalMapper(networkData: CollectionsResponse, page: Int, type : CollectionType): CollectionsData {
        return CollectionsData(
            page,
            networkData.id,
            networkData.title,
            networkData.totalPhotos,
            networkData.user.name,
            networkData.coverPhoto.urls.small,
            type.toString()
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