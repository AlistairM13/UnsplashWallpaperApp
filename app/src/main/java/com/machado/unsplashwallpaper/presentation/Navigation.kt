package com.machado.unsplashwallpaper.presentation

import androidx.annotation.DrawableRes
import com.machado.unsplashwallpaper.R


sealed class Screens(val route: String, val label: String, @DrawableRes val icon: Int) {
    object WallpaperListScreen : Screens("WallpaperListScreen", "Wallpapers", R.drawable.icon_list)
    object FavoritesScreen : Screens("FavouriteScreen", "Favorites", R.drawable.icon_favorite)
    object WallpaperSearchScreen : Screens("WallpaperSearchScreen", "Search", R.drawable.icon_search)
    object WallpaperDetailScreen : Screens("WallpaperDetailScreen", "WallpaperDetails", R.drawable.icon_list)
}