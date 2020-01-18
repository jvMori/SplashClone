package com.jvmori.myapplication.data.local

import androidx.room.*

@Dao
interface PhotosDao {
    @Query("Select * from photos_table where photo_page like :page")
    suspend fun getPhotos(page: Int = 1): List<PhotoData>

    @Transaction
    suspend fun updatePhotos(data: List<PhotoData>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    suspend fun insert(data: List<PhotoData>)

    @Query("Delete from photos_table where photo_page like :page")
    suspend fun delete(page: Int)
}