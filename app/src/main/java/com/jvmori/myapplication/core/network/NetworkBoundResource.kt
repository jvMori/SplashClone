package com.jvmori.myapplication.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jvmori.myapplication.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

suspend fun <RequestType, ResultType> getData(
    fetchLocalData: suspend () -> Flow<ResultType>,
    fetchNetworkData: suspend () -> Flow<RequestType>,
    refreshNeeded: (ResultType) -> Boolean,
    dataMapper: (RequestType) -> ResultType,
    saveCallResult: suspend (ResultType) -> Unit
): LiveData<Result<ResultType>> {
    return liveData(Dispatchers.IO) {
        fetchLocalData.invoke().onEach {
            emit(Result.success(it))
            if (refreshNeeded(it)) {
                try {
                    fetchNetworkData.invoke()
                        .map { data -> dataMapper(data) }
                        .onEach { networkData ->
                            saveCallResult(networkData)
                            emit(Result.success(networkData))
                        }
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }
        }
    }
}

suspend fun <RequestType, ResultType> fetchData(
    fetchLocalData: suspend () -> ResultType,
    fetchNetworkData: suspend () -> RequestType,
    refreshNeeded: (ResultType) -> Boolean,
    dataMapper: (RequestType) -> ResultType,
    saveCallResult: suspend (ResultType) -> Unit
): Resource<ResultType> {
    var data: Resource<ResultType> = Resource.loading(null)
    withContext(Dispatchers.IO){
        try {
            val local = fetchLocalData()
            data = if (refreshNeeded(local))
                networkRequest(fetchNetworkData, dataMapper, saveCallResult)
            else
                Resource.success(local)
        } catch (e: Exception) {
            networkRequest(fetchNetworkData, dataMapper, saveCallResult)
        }
    }
    return data
}

private suspend fun <RequestType, ResultType> networkRequest(
    fetchNetworkData: suspend () -> RequestType,
    dataMapper: (RequestType) -> ResultType,
    saveCallResult: suspend (ResultType) -> Unit
): Resource<ResultType> {
    val data: Resource<ResultType>
    data = try {
        val network = fetchNetworkData()
        val result = dataMapper(network)
        saveCallResult(result)
        Resource.success(result)
    } catch (e: java.lang.Exception) {
        Resource.error(e.localizedMessage, null)
    }
    return data
}


