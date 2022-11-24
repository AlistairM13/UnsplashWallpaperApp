package com.alistair.unsplashwallpaper.api

import com.alistair.unsplashwallpaper.models.UnsplashResponse
import com.alistair.unsplashwallpaper.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface UnsplashAPI {

    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") pageNumber: Int = 1,
        @Query("client_id") apiKey : String = API_KEY
    ): Response<UnsplashResponse>

    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") pageNumber: Int = 1,
        @Query("query") searchImage: String,
        @Query("orientation") orientation: String = "portrait",
        @Query("client_id") apiKey : String = API_KEY
    ): Response<UnsplashResponse>

}