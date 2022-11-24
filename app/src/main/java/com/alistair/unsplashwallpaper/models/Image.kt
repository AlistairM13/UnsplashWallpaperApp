package com.alistair.unsplashwallpaper.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "images")
data class Image(
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null,
    val description: String,
    val likes: Int,
    val links: Link,
    val urls: Url,
)