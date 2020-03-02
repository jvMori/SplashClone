package com.jvmori.myapplication.common.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <RequestType, LocalType, ResultType> fetchData(
    fetchLocalData: suspend () -> LocalType,
    fetchNetworkData: suspend () -> RequestType,
    refreshNeeded: (LocalType) -> Boolean,
    dataMapper: (RequestType) -> LocalType,
    saveCallResult: suspend (LocalType) -> Unit,
    resultDataMapper: (LocalType) -> ResultType
): Resource<ResultType> {
    var data: Resource<ResultType> =
        Resource.loading(null)
    withContext(Dispatchers.IO) {
         data = try {
            val local = fetchLocalData()
            if (refreshNeeded(local))
                networkRequest(
                    fetchNetworkData,
                    dataMapper,
                    saveCallResult,
                    resultDataMapper
                )
             Resource.success(resultDataMapper(local))
        } catch (e: Exception) {
             Resource.error(e.localizedMessage, null)
        }
    }
    return data
}

suspend fun <RequestType, LocalType, ResultType> networkRequest(
    fetchNetworkData: suspend () -> RequestType,
    dataMapper: (RequestType) -> LocalType,
    saveCallResult: suspend (LocalType) -> Unit,
    resultDataMapper: (LocalType) -> ResultType
): Resource<ResultType> {
    val data: Resource<ResultType>
    data = try {
        val network = fetchNetworkData()
        val local = dataMapper(network)
        saveCallResult(local)
        Resource.success(resultDataMapper(local))
    } catch (e: java.lang.Exception) {
        Resource.error(e.localizedMessage, null)
    }
    return data
}


