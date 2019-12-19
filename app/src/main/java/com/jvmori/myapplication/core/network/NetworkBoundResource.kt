package com.jvmori.myapplication.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

suspend fun<RequestType, ResultType> getData(
    fetchLocalData: suspend () -> Flow<ResultType>,
    fetchNetworkData: suspend () -> Flow<RequestType>,
    refreshNeeded: (ResultType) -> Boolean,
    dataMapper: (RequestType) -> ResultType,
    saveCallResult: suspend (ResultType) -> Unit
) : LiveData<Result<ResultType>> {
   return liveData(Dispatchers.IO) {
       fetchLocalData.invoke().onEach {
           emit(Result.success(it))
           if (refreshNeeded(it)){
               try {
                   fetchNetworkData.invoke()
                       .map { data -> dataMapper(data) }
                       .onEach { networkData ->
                           saveCallResult(networkData)
                           emit(Result.success(networkData))
                       }
               }catch (e : Exception){
                   emit(Result.failure(e))
               }
           }
       }
   }
}


