package com.machado.unsplashwallpaper.presentation.wallpaper_list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.machado.unsplashwallpaper.presentation.UnsplashViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperListScreen(navController: NavHostController) {
    val viewModel: UnsplashViewModel = hiltViewModel()
    val images = viewModel.imageList
    val isLoading = viewModel.isLoading
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyColumn() {
            items(images) {
                GlideImage(
                    model = it.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}