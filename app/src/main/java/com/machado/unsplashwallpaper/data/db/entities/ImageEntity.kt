package com.machado.unsplashwallpaper.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.machado.unsplashwallpaper.domain.model.ImageModel

@Entity
data class ImageEntity(
    @PrimaryKey val id: String,
    val url: String,
    val downloadUrl: String
) {
    fun toImageModel(): ImageModel {
        return ImageModel(
            id = id,
            url = url,
            downloadUrl = downloadUrl
        )
    }
}
