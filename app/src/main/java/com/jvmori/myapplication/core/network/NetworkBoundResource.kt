package com.jvmori.myapplication.core.network

import com.jvmori.myapplication.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <RequestType, ResultType> fetchData(
    fetchLocalData: suspend () -> ResultType,
    fetchNetworkData: suspend () -> RequestType,
    refreshNeeded: (ResultType) -> Boolean,
    dataMapper: (RequestType) -> ResultType,
    saveCallResult: suspend (ResultType) -> Unit
): Resource<ResultType> {
    var data: Resource<ResultType> = Resource.loading(null)
    withContext(Dispatchers.IO){
        data = try {
            val local = fetchLocalData()
            if (refreshNeeded(local))
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


