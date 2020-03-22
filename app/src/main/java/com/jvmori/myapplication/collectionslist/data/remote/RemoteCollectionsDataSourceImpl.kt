package com.jvmori.myapplication.collectionslist.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.Resource
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteCollectionsDataSourceImpl(private val api: CollectionsApi) :
    RemoteCollectionsDataSource {
    override suspend fun getCollections(page: Int): Resource<List<CollectionsResponse>> {
        return try {
            Resource.loading(null)
            val data = api.getCollections(page)
            Resource.success(data)
        } catch (e: Exception) {
            if (e is SocketTimeoutException || e is NetworkErrorException || e is HttpException || e is UnknownHostException)
                Resource.networkError(null, e.localizedMessage)
            else
                Resource.error(e.localizedMessage ?: "general error!", null)
        }
    }

    override suspend fun getFeaturedCollections(page: Int): Resource<List<CollectionsResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}