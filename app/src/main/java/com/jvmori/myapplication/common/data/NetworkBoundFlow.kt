package com.jvmori.myapplication.common.data

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.local.ICountTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.math.abs

suspend fun <Result, LocalData, NetworkData> fetchData(
    localData: () -> Flow<LocalData>,
    networkData: () -> Flow<NetworkData>,
    saveData: suspend (data: LocalData) -> Unit,
    networkToLocalMapper: (data: NetworkData) -> LocalData,
    localToResultMapper: (data: LocalData) -> Result
): Flow<Resource<Result>> {
    suspend fun fetchFromNetwork() {
        networkData().flowOn(Dispatchers.IO)
            .map {
                networkToLocalMapper(it)
            }.collect {
                saveData(it)
            }
    }
    return flow {
        try {
            localData().flowOn(Dispatchers.IO)
                .collect {
                    if (refreshNeeded(it as ICountTime)) {
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

fun refreshNeeded(data: ICountTime) :Boolean {
    return abs(System.currentTimeMillis() - data.timestamp) > 3600000
}

private fun <Result> handleError(e: Exception): Resource<Result> {
    if (e is NetworkErrorException || e is SocketTimeoutException || e is HttpException) {
        return Resource.networkError(null, e.localizedMessage)
    }
    return Resource.error(e.localizedMessage, null)
}

