package com.alistair.unsplashwallpaper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alistair.unsplashwallpaper.repository.ImageRepository

class ImageViewModelProviderFactory(private val imageRepository: ImageRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepository) as T
    }
}