package com.machado.unsplashwallpaper.presentation.wallpaper_list_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.machado.unsplashwallpaper.presentation.PAGE_SIZE
import com.machado.unsplashwallpaper.presentation.Screens
import com.machado.unsplashwallpaper.presentation.UnsplashViewModel

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun WallpaperListScreen(
    navController: NavHostController,
    viewModel: UnsplashViewModel,
    orderBy: UnsplashViewModel.ImageListOrder
) {

    val images = viewModel.imageList
    val isLoading = viewModel.isLoading
    val page = viewModel.page.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
            itemsIndexed(images) { index, image ->
                viewModel.onChangeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !isLoading) {
                    viewModel.nextPage(orderBy)
                }
                GlideImage(
                    model = image.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            viewModel.setSelectedImage(image)
                            navController.navigate(Screens.WallpaperDetailScreen.route)
                        }
                )
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}