package com.jvmori.myapplication.collectionslist.data.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface CollectionsDao {

    @Query("Select * from collections_table")
    fun getCollections(): PagingSource<Int, CollectionsData>

    @Query("Select * from collections_table where collection_type like :type")
    fun getFeaturedCollections(type: String): PagingSource<Int, CollectionsData>

    @Transaction
    suspend fun updateCollection(data: List<CollectionsData>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    suspend fun insert(collections: List<CollectionsData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: CollectionsData)

    @Query("Delete from collections_table where collection_page like :page")
    suspend fun delete(page: Int)
}