package com.jvmori.myapplication.common.data

import android.accounts.NetworkErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

fun <Result, LocalData, NetworkData> fetchData(
    localData: () -> Flow<LocalData>,
    refreshNeeded: (data: LocalData) -> Boolean,
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

private fun <Result> handleError(e: Exception): Resource<Result> {
    if (e is NetworkErrorException || e is SocketTimeoutException || e is HttpException) {
        return Resource.networkError(null, e.localizedMessage)
    }
    return Resource.error(e.localizedMessage, null)
}

