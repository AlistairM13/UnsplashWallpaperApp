package com.machado.unsplashwallpaper.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.machado.unsplashwallpaper.data.db.UnsplashDatabase
import com.machado.unsplashwallpaper.data.remote.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UnsplashModule {
    @Provides
    @Singleton
    fun providesUnsplashService(): UnsplashService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("https://api.unsplash.com/")
            .build()
            .create(UnsplashService::class.java)
    }

    @Provides
    @Singleton
    fun providesUnsplashDatabase(application: Application): UnsplashDatabase {
        return Room.databaseBuilder(
            application,
            UnsplashDatabase::class.java,
            "unsplash_db"
        ).build()
    }
}