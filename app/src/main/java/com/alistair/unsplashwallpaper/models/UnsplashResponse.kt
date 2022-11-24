package com.alistair.unsplashwallpaper.models

class UnsplashResponse : ArrayList<UnsplashImagesItem>()

data class UnsplashImagesItem(
    val description: String,
    val id: String,
    val likes: Int,
    val links: Links,
    val urls: Urls,
)

data class Links(
    val download: String,
)

data class Urls(
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)