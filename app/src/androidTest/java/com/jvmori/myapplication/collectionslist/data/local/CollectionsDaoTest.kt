package com.jvmori.myapplication.collectionslist.data.local

import com.jvmori.myapplication.common.data.PhotosDatabase
import com.jvmori.myapplication.util.databaseTestModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CollectionsDaoTest : KoinTest {
    private val photosDatabase: PhotosDatabase by inject()
    private val collectionsDao: CollectionsDao by inject()

    @Before
    fun before() {
        loadKoinModules(databaseTestModule)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_saving_data_successfully_then_get_data_returns_success_result(){
        runBlocking {
            //Arrange
            val page = 1
            val dataToSave = listOf(CollectionsData(page, 0), CollectionsData(page, 1))

            //Act
            collectionsDao.updateCollection(dataToSave)
            val result = async {
                collectionsDao.getCollections(page).take(2).toList()
            }

            //Assert
            assertEquals(dataToSave, result.await())
        }
    }

    @Test
    fun when_deleting_data_successfully_then_get_data_returns_zero_results(){
        runBlocking {
            //Arrange
            val page = 1
            val dataToSave = listOf(CollectionsData(page, 0), CollectionsData(page, 1))

            //Act
            collectionsDao.updateCollection(dataToSave)
            collectionsDao.delete(page)
            val result = async {
                collectionsDao.getCollections(page)
                    .toList()
            }

            //Assert
            assertEquals(result.await().size, 0)
        }
    }
}