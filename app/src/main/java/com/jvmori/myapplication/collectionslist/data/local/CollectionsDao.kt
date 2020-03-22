package com.jvmori.myapplication.collectionslist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CollectionsDao {

    @Query("Select * from collections_table where collection_page like :page")
    fun getCollections(page: Int): List<CollectionsData>

    @Transaction
    fun updateCollection(data: List<CollectionsData>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    fun insert(collections: List<CollectionsData>)

    @Query("Delete from collections_table where collection_page like :page")
    fun delete(page: Int)
}