package com.machado.unsplashwallpaper.presentation.wallpaper_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.machado.unsplashwallpaper.R
import com.machado.unsplashwallpaper.domain.model.ImageModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperDetailScreen(
    selectedImage: ImageModel?,
    saveImage: (ImageModel) -> Unit,
    downloadImage: (ImageModel) -> Unit,
    isSaved: Boolean,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        selectedImage?.let {
            GlideImage(
                model = selectedImage.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
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
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = null,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                            .clickable {
                                saveImage(selectedImage)
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
                                downloadImage(selectedImage)
                            }
                    )
                }
            }
        }
    }
}
