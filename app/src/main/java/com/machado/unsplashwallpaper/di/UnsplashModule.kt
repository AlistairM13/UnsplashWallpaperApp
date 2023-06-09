package com.machado.unsplashwallpaper.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.machado.unsplashwallpaper.BuildConfig

import com.machado.unsplashwallpaper.data.db.UnsplashDatabase
import com.machado.unsplashwallpaper.data.remote.UnsplashService
import com.machado.unsplashwallpaper.data.repository_Impl.UnsplashRepositoryImpl
import com.machado.unsplashwallpaper.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Provides
    @Singleton
    @Named("api_key")
    fun providesApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun providesUnsplashRepository(
        api: UnsplashService,
        db: UnsplashDatabase
    ): UnsplashRepository {
        return UnsplashRepositoryImpl(
            api = api,
            dao = db.dao
        )
    }
}