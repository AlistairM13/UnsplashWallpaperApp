package com.alistair.unsplashwallpaper.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alistair.unsplashwallpaper.models.Image

interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(image: Image): Long

    @Query("SELECT * FROM images")
    fun getAllImages(): LiveData<List<Image>>

    @Delete
    suspend fun deleteImage(image:Image)

}