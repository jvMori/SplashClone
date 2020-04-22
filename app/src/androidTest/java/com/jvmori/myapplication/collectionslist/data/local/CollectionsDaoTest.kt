package com.jvmori.myapplication.collectionslist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.jvmori.myapplication.common.data.local.PhotosDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CollectionsDaoTest : KoinTest {

    private val collectionsDao: CollectionsDao by inject()

    @Before
    fun before() {
        loadKoinModules(databaseTestModule)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Test
    fun when_saving_data_successfully_then_get_data_returns_success_result() {
        runBlocking {
            //Arrange
            val page = 1
            val dataToSave = listOf(
                CollectionsData(
                    1,
                    1,
                    "title",
                    20,
                    "name",
                    "link"
                )
            )

            //Act
            collectionsDao.updateCollection(dataToSave)
            val collectionsData = mutableListOf<CollectionsData>()
            collectionsDao.getCollections().map {
                collectionsData.add(it)
            }

            //Assert
            assertTrue(collectionsData.isNotEmpty())
            assertTrue(collectionsData.containsAll(dataToSave))
        }
    }

    @Test
    fun when_deleting_data_successfully_then_get_data_returns_zero_results() {
        runBlocking {
            //Arrange
            val page = 1
            val dataToSave = listOf(CollectionsData(page, 0), CollectionsData(page, 1))

            //Act
            collectionsDao.updateCollection(dataToSave)
            collectionsDao.delete(page)

            val collectionsData = mutableListOf<CollectionsData>()
            collectionsDao.getCollections().map {
                collectionsData.add(it)
            }

            //Assert
            assertTrue(collectionsData.size == 0)
        }
    }

    private val databaseTestModule = module {
        single(override = true) {
            Room.inMemoryDatabaseBuilder(androidApplication().applicationContext, PhotosDatabase::class.java)
                .setTransactionExecutor(testDispatcher.asExecutor())
                .setQueryExecutor(testDispatcher.asExecutor())
                .build()
        }
    }
}