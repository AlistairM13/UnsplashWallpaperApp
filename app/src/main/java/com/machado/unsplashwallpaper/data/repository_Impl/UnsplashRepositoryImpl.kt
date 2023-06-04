package com.machado.unsplashwallpaper.data.repository_Impl

import com.machado.unsplashwallpaper.data.db.UnsplashDao
import com.machado.unsplashwallpaper.data.db.entities.ImageEntity
import com.machado.unsplashwallpaper.data.remote.UnsplashService
import com.machado.unsplashwallpaper.data.remote.dto.ImageDto
import com.machado.unsplashwallpaper.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    val api: UnsplashService,
    val dao: UnsplashDao
) : UnsplashRepository {
    override suspend fun getImagesFromApi(apiKey: String, page: Int): Response<List<ImageDto>> =
        api.getImageList(apiKey, page)

    override suspend fun searchImages(
        apiKey: String,
        query: String,
        page: Int
    ): Response<List<ImageDto>> = api.searchImage(apiKey, query, page)

    override suspend fun saveImage(imageEntity: ImageEntity) = dao.saveImage(imageEntity)

    override suspend fun unSaveImage(imageEntity: ImageEntity) = dao.unSaveImage(imageEntity)

    override fun getSavedImages(): Flow<List<ImageEntity>> = dao.getSavedImages()

    override suspend fun getImageById(imageId: String): ImageEntity? = dao.getImageById(imageId)
}