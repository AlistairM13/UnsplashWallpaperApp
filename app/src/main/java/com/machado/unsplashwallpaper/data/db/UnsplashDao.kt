package com.machado.unsplashwallpaper.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.machado.unsplashwallpaper.data.db.entities.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImage(imageEntity: ImageEntity)

    @Delete
    suspend fun unSaveImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    fun getSavedImages(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM ImageEntity WHERE id=:imageId")
    suspend fun getImageById(imageId: String): ImageEntity?

}