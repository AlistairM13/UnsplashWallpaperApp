package com.alistair.unsplashwallpaper.ui

import androidx.lifecycle.ViewModel
import com.alistair.unsplashwallpaper.repository.ImageRepository

class ImageViewModel(
    val imageRepository : ImageRepository
): ViewModel() {
}