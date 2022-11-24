package com.alistair.unsplashwallpaper.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "images")
data class Image(
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null,
    val description: String = "No Description Provided",
//    val likes: Int, // sort by likes feature later on
    val links: Link,
    val urls: Url,
)