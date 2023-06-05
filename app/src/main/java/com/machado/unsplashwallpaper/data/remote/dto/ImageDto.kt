package com.machado.unsplashwallpaper.data.remote.dto

import com.machado.unsplashwallpaper.domain.model.ImageModel

data class ImageDto(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val current_user_collections: List<CurrentUserCollection>,
    val description: String?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
) {
    fun toImageModel(): ImageModel {
        return ImageModel(
            id = id,
            url = urls.regular,
            downloadUrl = getDownloadUrl()
        )
    }

    private fun getDownloadUrl(): String {
        val url = urls.regular
        val imageId = id
        val createdBy = user.name.lowercase().split(" ").joinToString("-")
        return "$url&dl=$createdBy-$imageId-unsplash.jpg"
    }
}