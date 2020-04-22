package com.jvmori.myapplication.collectionslist.data.local

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface CollectionsDao {

    @Query("Select * from collections_table")
    fun getCollections(): DataSource.Factory<Int, CollectionsData>

//    @Query("Select * from collections_table where collection_page like :page")
//    fun getCollections(page: Int): DataSource.Factory<Int, CollectionsData>

    @Transaction
    fun updateCollection(data: List<CollectionsData>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    fun insert(collections: List<CollectionsData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(collection: CollectionsData)

    @Query("Delete from collections_table where collection_page like :page")
    fun delete(page: Int)
}