package com.machado.unsplashwallpaper.data.remote.dto

data class SearchResult(
    val total: Int,
    val totalPages: Int,
    val results: List<ImageDto>
)