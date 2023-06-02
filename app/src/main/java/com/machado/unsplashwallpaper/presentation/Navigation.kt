package com.machado.unsplashwallpaper.presentation

import androidx.annotation.DrawableRes
import com.machado.unsplashwallpaper.R


sealed class Screens(val route:String, val label:String, @DrawableRes val icon: Int){
    object WallpaperListScreens: Screens("WallpaperListScreen", "Wallpapers", R.drawable.icon_list)
    object FavoritesScreens: Screens("FavouriteScreen", "Favorites", R.drawable.icon_favorite)
    object WallpaperDetailScreens: Screens("WallpaperDetailScreen", "WallpaperDetails", R.drawable.icon_list)
}