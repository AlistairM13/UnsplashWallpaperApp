package com.machado.unsplashwallpaper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.machado.unsplashwallpaper.data.db.entities.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1
)
abstract class UnsplashDatabase : RoomDatabase() {
    abstract val dao: UnsplashDao
}