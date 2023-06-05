package com.machado.unsplashwallpaper.presentation.wallpaper_detail_screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.machado.unsplashwallpaper.R
import com.machado.unsplashwallpaper.domain.model.ImageModel
import com.machado.unsplashwallpaper.presentation.UnsplashViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperDetailScreen(
    viewModel: UnsplashViewModel
) {
    val isSaved = viewModel.isSaved
    val selectedImage = viewModel.selectedImage
    val context = LocalContext.current
    val showButtons = remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        selectedImage.value?.let {
            GlideImage(
                model = it.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        showButtons.value = !showButtons.value
                    }
            )
            AnimatedVisibility(
                visible = showButtons.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SaveAndDownloadButtons(
                    selectedImage = it,
                    isSaved = isSaved,
                    viewModel = viewModel,
                    context = context
                )
            }

        }
    }
}

@Composable
fun SaveAndDownloadButtons(
    selectedImage: ImageModel,
    isSaved: Boolean,
    viewModel: UnsplashViewModel,
    context: Context
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black
                    )
                )
            )
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painterResource(id = if (isSaved) R.drawable.icon_favorite else R.drawable.icon_outline_favorite),
                colorFilter = if (isSaved) ColorFilter.tint(Color.Red) else ColorFilter.tint(
                    Color.White
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewModel.saveImage(selectedImage)
                    }
            )
            Image(
                painterResource(id = R.drawable.icon_download),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        viewModel.downloadImage(selectedImage, context)
                    }
            )
        }
    }
}
