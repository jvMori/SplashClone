package com.jvmori.myapplication.data.remote

import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class NetworkBoundResourceKtTest<ResultType> {

    lateinit var localDataFunc: suspend () -> List<PhotoEntity>
    @Before
    fun setup() {
        val entity = mock(PhotoEntity::class.java)
        val list = arrayListOf(entity, entity, entity)
        localDataFunc = {
            list
        }
    }

    @Test
    fun testFetchDataWhenRefreshNeeded() {
        runBlocking {
            val local = localDataFunc()

        }
    }

    @Test
    fun testFetchRemoteData() {
        //get network data
        //if success save data
    }
}