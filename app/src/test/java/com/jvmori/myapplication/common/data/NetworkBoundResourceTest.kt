package com.jvmori.myapplication.common.data

import com.jvmori.myapplication.data.source.FakeLocalDataSource
import com.jvmori.myapplication.photoslist.data.local.PhotoData
import com.jvmori.myapplication.photoslist.data.remote.photodata.PhotoDataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class NetworkBoundResourceTest {

    private val remoteData = listOf(mock(PhotoDataResponse::class.java))
    private val localData = listOf(mock(PhotoData::class.java))
    private val localDataSource = FakeLocalDataSource(localData)
    private val networkBoundResource = mock(NetworkBoundResource::class.java)

    @ExperimentalCoroutinesApi
    @Test
    fun fetchData_refreshNeeded_isRefreshed() {
        runBlocking {
            networkBoundResource.fetchData(
                { fetchLocalData() },
                { fetchNetworkData() },
                { refreshNeeded(true) },
                {
                    dataMapper(remoteData)
                },
                {
                    saveCallResult(localData)
                },
                {
                    localData
                }
            )

            verify(networkBoundResource, atLeastOnce()).networkRequest({
                fetchNetworkData()
            }, {
                dataMapper(remoteData)
            }, {
                saveCallResult(localData)
            }, {
                localData
            })

        }
    }

    private suspend fun saveCallResult(localData: List<PhotoData>) {
        localDataSource.update(localData)
    }

    private fun dataMapper(networkData: List<PhotoDataResponse>): List<PhotoData> {
        return localData
    }

    private suspend fun fetchLocalData(): List<PhotoData> {
        return localData
    }

    private suspend fun fetchNetworkData(): List<PhotoDataResponse> {
        return remoteData
    }

    private fun refreshNeeded(refresh: Boolean): Boolean {
        return refresh
    }
}