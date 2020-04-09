package com.jvmori.myapplication.collectionslist.data.remote

import android.util.Log
import com.jvmori.myapplication.collectionslist.data.remote.response.CollectionsResponse
import com.jvmori.myapplication.collectionslist.domain.repositories.RemoteCollectionsDataSource
import com.jvmori.myapplication.common.data.Resource
import com.jvmori.myapplication.common.data.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RemoteCollectionsDataSourceImpl(private val api: CollectionsApi) :
    RemoteCollectionsDataSource {
    override fun getCollections(page: Int): Flow<Resource<List<CollectionsResponse>>> {
        return flow {
            try {
                val result = api.getCollections(page)
                emit(Resource.success(result))
            } catch (e: Exception) {
                emit(handleError(e))
                Log.i("ex", e.localizedMessage)
            }
        }
    }

    override fun getFeaturedCollections(page: Int): Flow<List<CollectionsResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}