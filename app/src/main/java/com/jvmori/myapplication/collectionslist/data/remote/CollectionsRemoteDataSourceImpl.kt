package com.jvmori.myapplication.collectionslist.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.entities.CollectionEntity
import com.jvmori.myapplication.collectionslist.domain.repositories.CollectionsRemoteDataSource
import com.jvmori.myapplication.common.data.Resource
import java.lang.Exception

class CollectionsRemoteDataSourceImpl(private val api: CollectionsApi) :
    CollectionsRemoteDataSource {
    override suspend fun getCollections(page: Int): Resource<List<CollectionsResponse>> {
        return try {
            Resource.loading(null)
            val data = api.getCollections(page)
            Resource.success(data)
        } catch (e: NetworkErrorException) {
            Resource.networkError(null, e.localizedMessage)
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
    }

    override suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionsResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}