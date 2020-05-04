package com.jvmori.myapplication.search.data.remote

import android.accounts.NetworkErrorException
import com.jvmori.myapplication.photoslist.domain.entities.PhotoEntity
import com.jvmori.myapplication.search.data.Orientation
import com.jvmori.myapplication.search.data.PhotoParams
import com.jvmori.myapplication.search.domain.repositories.SearchRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchRemoteDataSourceImplTest {

    private lateinit var searchRemoteDataSource: SearchRemoteDataSource

    @Mock
    lateinit var api: SearchApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchRemoteDataSource = SearchRemoteDataSourceImpl(api)
    }

    @Test
    fun `when search photos success then return list of data`() {
        runBlocking {
            //arrange
            val data = listOf<PhotoEntity>(PhotoEntity("", "", 1))
            Mockito.`when`(api.searchPhotos(ArgumentMatchers.anyMap())).thenReturn(data)

            //act
            val params = PhotoParams("", 1, Orientation.All)
            val result = searchRemoteDataSource.searchPhotos(params)

            //assert
            assert(result.isNotEmpty())
            assert(result == data)
        }
    }

    @Test
    fun `when search photos network error then throw exception`() {
        runBlocking {
            Mockito.`when`(api.searchPhotos(ArgumentMatchers.anyMap())).then {
                throw NetworkErrorException()
            }

            //act
            val params = PhotoParams("", 1, Orientation.All)
            val result: List<PhotoEntity> = try {
                searchRemoteDataSource.searchPhotos(params)
            } catch (e: NetworkErrorException) {
                listOf<PhotoEntity>()
            }

            //assert
            assert(result.isEmpty())
        }
    }
}