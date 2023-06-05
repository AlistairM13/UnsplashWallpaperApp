package com.machado.unsplashwallpaper.data.remote

import com.machado.unsplashwallpaper.data.remote.dto.ImageDto
import com.machado.unsplashwallpaper.data.remote.dto.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("/photos")
    suspend fun getImageList(
        @Query("client_id") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("order_by") orderBy: String = "popular",
        @Query("per_page") pageSize: Int = 30
    ): Response<List<ImageDto>>

    @GET("/search/photos")
    suspend fun searchImage(
        @Query("client_id") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 30
    ): Response<SearchResult>

}