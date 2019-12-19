package com.jvmori.myapplication.features.photolist.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import com.jvmori.myapplication.features.photolist.domain.entities.PhotoEntity

@Dao
interface PhotosDao {
    @Query("Select * from photos_table where photo_page like :page")
    suspend fun getPhotos(page : Int = 1) : List<PhotoEntity>
}