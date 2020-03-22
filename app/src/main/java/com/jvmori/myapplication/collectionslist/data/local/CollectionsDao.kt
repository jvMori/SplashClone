package com.jvmori.myapplication.collectionslist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class CollectionsDao {

    @Query("Select * from collections_table where page like :page")
    abstract fun getCollections(page: Int): List<CollectionsData>

    @Transaction
    fun updateCollection(data: List<CollectionsData>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    abstract fun insert(collections: List<CollectionsData>)

    @Query("Delete from collections_table where page like :page")
    abstract fun delete(page: Int)
}