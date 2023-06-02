package com.machado.unsplashwallpaper.data.db.entities

import androidx.room.Entity
import com.machado.unsplashwallpaper.domain.model.ImageModel

@Entity
data class ImageEntity(
    val id: String,
    val url: String
) {
    fun toImageModel(): ImageModel {
        return ImageModel(
            id = id,
            url = url
        )
    }
}
