package com.jvmori.myapplication.features.photolist.data.datasources.local

import androidx.room.*
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity

@Dao
interface PhotosDao {
    @Query("Select * from photos_table where photo_page like :page")
    suspend fun getPhotos(page: Int = 1): List<PhotoEntity>

    @Transaction
    suspend fun updatePhotos(data: List<PhotoEntity>) {
        if (data.isNotEmpty()) {
            delete(data[0].page)
            insert(data)
        }
    }

    @Insert
    suspend fun insert(data: List<PhotoEntity>)

    @Query("Delete from photos_table where photo_page like :page")
    suspend fun delete(page: Int)
}