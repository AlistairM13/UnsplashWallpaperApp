package com.machado.unsplashwallpaper.presentation.search_images

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.machado.unsplashwallpaper.presentation.PAGE_SIZE
import com.machado.unsplashwallpaper.presentation.Screens
import com.machado.unsplashwallpaper.presentation.UnsplashViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun WallpaperSearchScreen(navController: NavHostController, viewModel: UnsplashViewModel) {
    val isLoading = viewModel.isLoading
    val searchResults = viewModel.searchImages.value
    val page = viewModel.searchPage.value

    Box(modifier = Modifier.fillMaxSize()) {

        if (viewModel.searchQuery.isNotBlank() && searchResults.isEmpty() && !isLoading) {
            Text(text = "No Results found", modifier = Modifier.align(Alignment.Center))
        }
        Column {
            TextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearch(query = it) },
                label = { Text(text = "Search images") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                itemsIndexed(searchResults) { index, imageModel ->
                    viewModel.onChangeSearchScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !isLoading) {
                        viewModel.nextSearchPage()
                    }
                    GlideImage(
                        model = imageModel.url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.setSelectedImage(imageModel)
                                navController.navigate(Screens.WallpaperDetailScreen.route)
                            }
                    )
                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}