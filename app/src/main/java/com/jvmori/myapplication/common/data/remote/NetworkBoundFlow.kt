package com.jvmori.myapplication.common.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.collectionslist.data.local.ICountTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.abs

fun <Result, LocalData, NetworkData> fetchData(
    localData: () -> Flow<List<LocalData>>,
    networkData: () -> Flow<Resource<List<NetworkData>>>,
    saveData: suspend (data: List<LocalData>) -> Unit,
    networkToLocalMapper: (data: List<NetworkData>) -> List<LocalData>,
    localToResultMapper: (data: List<LocalData>) -> List<Result>
): Flow<Resource<List<Result>>> {

    suspend fun fetchFromNetwork(): Resource.Status {
        var status: Resource.Status =
            Resource.Status.LOADING
        withContext(Dispatchers.IO) {
            networkData().collect {
                if (it.status == Resource.Status.SUCCESS) {
                    val local = networkToLocalMapper(it.data!!)
                    saveData(local)
                }
                status = it.status!!
            }
        }
        return status
    }
    return flow {
        localData()
            .catch { handleError<Result>(it) }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .collect {
                if (refreshNeeded(it)) {
                    val status = fetchFromNetwork()
                    val result = localToResultMapper(it)
                    emitResult(status, result)
                }else {
                    emit(Resource.success(localToResultMapper(it)))
                }
            }
    }
}

private suspend fun <Result> FlowCollector<Resource<List<Result>>>.emitResult(
    status: Resource.Status,
    result: List<Result>
) {
    when (status) {
        is Resource.Status.NETWORK_ERROR -> emit(
            Resource.networkError(
                result,
                "Network error"
            )
        )
        is Resource.Status.ERROR -> emit(
            Resource.error(
                "General error",
                result
            )
        )
    }
}

fun <LocalData> refreshNeeded(data: List<LocalData>): Boolean {
    if (data.isNotEmpty() && data[0] is ICountTime) {
        return abs(System.currentTimeMillis() - (data[0] as ICountTime).timestamp) > 3600000
    }
    return data.isEmpty()
}

fun <LocalData> refreshNeeded(data: LocalData): Boolean {
    if (data is ICountTime) {
        return abs(System.currentTimeMillis() - (data as ICountTime).timestamp) > 3600000
    }
    return false
}

fun <Result> handleError(e: Throwable?): Resource<Result> {
    if (e is NetworkErrorException || e is SocketTimeoutException || e is HttpException || e is UnknownHostException) {
        return Resource.networkError(
            null,
            e.localizedMessage
        )
    }
    return Resource.error(
        e?.localizedMessage ?: "",
        null
    )
}

