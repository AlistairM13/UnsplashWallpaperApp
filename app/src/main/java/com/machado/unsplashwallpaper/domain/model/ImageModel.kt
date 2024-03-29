package com.machado.unsplashwallpaper.domain.model

import com.machado.unsplashwallpaper.data.db.entities.ImageEntity

data class ImageModel(
    val id: String,
    val url: String,
    val downloadUrl: String = ""
) {
    fun toImageEntity(): ImageEntity {
        return ImageEntity(
            id = id,
            url = url,
            downloadUrl = downloadUrl
        )
    }
}