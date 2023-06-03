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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.machado.unsplashwallpaper.domain.model.ImageModel
import com.machado.unsplashwallpaper.presentation.favorites_screen.FavoritesScreen
import com.machado.unsplashwallpaper.presentation.theme.UnsplashWallpaperTheme
import com.machado.unsplashwallpaper.presentation.wallpaper_detail_screen.WallpaperDetailScreen
import com.machado.unsplashwallpaper.presentation.wallpaper_list_screen.WallpaperListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


// secret key RxNgJfogQGqawWrpFfovbZvgOJazExVqVBtDEQ6nCcc
// access key pmtX7wGq-0F-aGyjxop_J0Ki5cTrs-1mZLeQPudEntE
// image = imageUrl&dl=username-imageId-unsplash.jpg

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            var isDetailScreen by remember { mutableStateOf(false) }
            UnsplashWallpaperTheme{
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val viewModel: UnsplashViewModel = hiltViewModel()
                val snackBarHostState = remember { SnackbarHostState() }
                var selectedImage by remember { mutableStateOf<ImageModel?>(null) }
                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is UnsplashViewModel.UIEvent.ShowSnackBar -> {
                                snackBarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.WallpaperDetailScreens.route -> false // on this screen bottom bar should be hidden
                    else -> true // in all other cases show bottom bar
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screens.WallpaperListScreens.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screens.WallpaperListScreens.route) {
                            WallpaperListScreen(
                                navController
                            ) {
                                selectedImage = it
                            }
                        }
                        composable(Screens.FavoritesScreens.route) { FavoritesScreen(navController) }
                        composable(Screens.WallpaperDetailScreens.route) {
                            WallpaperDetailScreen(
                                selectedImage = selectedImage,
                                saveImage = { imageToBeSaved ->
                                    viewModel.saveImage(imageToBeSaved)
                                },
                                downloadImage = { imageToBeDownloaded ->
                                    viewModel.downloadImage(imageToBeDownloaded)
                                },
                                isSaved = viewModel.isSaved,
                            )
                        }
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


