package com.machado.unsplashwallpaper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.machado.unsplashwallpaper.presentation.favorites_screen.FavoritesScreen
import com.machado.unsplashwallpaper.presentation.ui.theme.UnsplashWallpaperTheme
import com.machado.unsplashwallpaper.presentation.wallpaper_detail_screen.WallpaperDetailScreen
import com.machado.unsplashwallpaper.presentation.wallpaper_list_screen.WallpaperListScreen


// secret key RxNgJfogQGqawWrpFfovbZvgOJazExVqVBtDEQ6nCcc
// access key pmtX7wGq-0F-aGyjxop_J0Ki5cTrs-1mZLeQPudEntE

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnsplashWallpaperTheme {
                // A surface container using the 'background' color from the theme
             val navController = rememberNavController()
                Scaffold(
bottomBar = { BottomNavigationBar(navController = navController)}
             ) {innerPadding->
                 NavHost(navController, startDestination = Screens.WallpaperListScreens.route, Modifier.padding(innerPadding)) {
                     composable(Screens.WallpaperListScreens.route) { WallpaperListScreen(navController) }
                     composable(Screens.FavoritesScreens.route) { FavoritesScreen(navController) }
                     composable(Screens.WallpaperDetailScreens.route) {WallpaperDetailScreen()}
                 }
             }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens: List<Screens> = listOf(Screens.WallpaperListScreens, Screens.FavoritesScreens)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.icon), contentDescription = "null") },
                label = { Text(text = screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


