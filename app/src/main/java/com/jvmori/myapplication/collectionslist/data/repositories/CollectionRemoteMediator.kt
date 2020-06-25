package com.jvmori.myapplication.collectionslist.data.repositories

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.jvmori.myapplication.collectionslist.data.CollectionType
import com.jvmori.myapplication.collectionslist.data.local.CollectionsData
import com.jvmori.myapplication.collectionslist.data.networkToLocalMapper
import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import retrofit2.HttpException
import java.io.IOException

class CollectionRemoteMediator(
    private val localCollectionsDataSource: LocalCollectionsDataSource,
    private val remoteCollectionsDataSource: RemoteCollectionsDataSource,
    private val collectionType: CollectionType
) : RemoteMediator<Int, CollectionsData>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, CollectionsData>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem =
                        state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page
                }
            }

            val response = remoteCollectionsDataSource.getCollections(page = loadKey, type = collectionType)
            localCollectionsDataSource.insertCollections(response.map {
                it.networkToLocalMapper(loadKey, collectionType)
            })

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}