package com.jvmori.myapplication.common.data

import android.accounts.NetworkErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface NetworkBoundResource<LocalType, RequestType, ResultType, Params> {
    suspend fun fetchLocalData(params: Params): LocalType
    suspend fun fetchNetworkData(params: Params): RequestType
    suspend fun refreshNeeded(data: LocalType): Boolean
    fun dataMapper(data: RequestType, params: Params): LocalType
    suspend fun saveCallResult(data: LocalType)
    fun resultDataMapper(data: LocalType): ResultType

    suspend fun fetchData(params: Params): Resource<ResultType> {
        var data: Resource<ResultType> =
            Resource.loading(null)
        withContext(Dispatchers.IO) {
            data = try {
                val local = fetchLocalData(params)
                if (refreshNeeded(local))
                    networkRequest(params)
                else
                    Resource.success(resultDataMapper(fetchLocalData(params)))
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.error(e.localizedMessage, null)
            }
        }
        return data
    }

    suspend fun networkRequest(params: Params): Resource<ResultType> {
        val data: Resource<ResultType>
        data = try {
            val network = fetchNetworkData(params)
            val local = dataMapper(network, params)
            saveCallResult(local)
            Resource.success(resultDataMapper(local))
        } catch (e: NetworkErrorException) {
            Resource.networkError(null, e.localizedMessage)
        } catch (e: Exception) {
            Resource.error(e.localizedMessage, null)
        }
        return data
    }
}


