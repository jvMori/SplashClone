package com.jvmori.myapplication.collectionslist.data.local

import com.jvmori.myapplication.collectionslist.domain.repositories.LocalCollectionsDataSource
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock

class LocalCollectionsDataSourceImplTest{

    private val dao: CollectionsDao = mock(CollectionsDao::class.java)
    private lateinit var localDataSource : LocalCollectionsDataSource

    @Before
    fun setup(){
        localDataSource = LocalCollectionsDataSourceImpl(dao)
    }



}