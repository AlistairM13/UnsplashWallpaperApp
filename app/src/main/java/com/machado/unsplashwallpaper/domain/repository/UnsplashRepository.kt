package com.machado.unsplashwallpaper.domain.repository

import com.machado.unsplashwallpaper.data.db.entities.ImageEntity
import com.machado.unsplashwallpaper.data.remote.dto.ImageDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UnsplashRepository {
    // Network ops
    suspend fun getImagesFromApi(apiKey: String, page: Int): Response<List<ImageDto>>

    suspend fun searchImages(apiKey: String, query: String, page: Int): Response<List<ImageDto>>

    // DB ops
    suspend fun saveImage(imageEntity: ImageEntity)

    suspend fun unSaveImage(imageEntity: ImageEntity)

    fun getSavedImages(): Flow<List<ImageEntity>>
}