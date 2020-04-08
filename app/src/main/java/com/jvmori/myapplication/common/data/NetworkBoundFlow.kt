package com.jvmori.myapplication.common.data

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.local.ICountTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.math.abs

fun <Result, LocalData, NetworkData> fetchData(
    localData: () -> Flow<List<LocalData>>,
    networkData: () -> Flow<List<NetworkData>>,
    saveData: suspend (data: List<LocalData>) -> Unit,
    networkToLocalMapper: (data: List<NetworkData>) -> List<LocalData>,
    localToResultMapper: (data: List<LocalData>) -> List<Result>
): Flow<Resource<List<Result>>> {
    suspend fun fetchFromNetwork() {
        networkData().flowOn(Dispatchers.IO)
            .map {
                networkToLocalMapper(it)
            }.collect {
                withContext(Dispatchers.IO) {
                    saveData(it)
                }
            }
    }
    return flow {
        try {
            localData().flowOn(Dispatchers.IO)
                .collect {
                    if (refreshNeeded(it)) {
                        fetchFromNetwork()
                    }
                    val result = localToResultMapper(it)
                    emit(Resource.success(result))
                }
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }
}

fun <LocalData> refreshNeeded(data: List<LocalData>): Boolean {
    if (data.isNotEmpty() && data[0] is ICountTime) {
        return abs(System.currentTimeMillis() - (data[0] as ICountTime).timestamp) > 3600000
    }
    return true
}

private fun <Result> handleError(e: Exception): Resource<Result> {
    if (e is NetworkErrorException || e is SocketTimeoutException || e is HttpException) {
        return Resource.networkError(null, e.localizedMessage)
    }
    return Resource.error(e.localizedMessage, null)
}

