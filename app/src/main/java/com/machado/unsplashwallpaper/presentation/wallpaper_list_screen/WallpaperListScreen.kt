package com.machado.unsplashwallpaper.presentation.wallpaper_list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.machado.unsplashwallpaper.presentation.Screens

@Composable
fun WallpaperListScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Wallpaper List Screen")
        Button(onClick = {
            navController.navigate(Screens.WallpaperDetailScreens.route)
        }) {
            Text(text = "Details")
        }
    }

}