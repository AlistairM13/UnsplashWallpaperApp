package com.machado.unsplashwallpaper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.machado.unsplashwallpaper.presentation.favorites_screen.FavoritesScreen
import com.machado.unsplashwallpaper.presentation.search_images.WallpaperSearchScreen
import com.machado.unsplashwallpaper.presentation.theme.UnsplashWallpaperTheme
import com.machado.unsplashwallpaper.presentation.wallpaper_detail_screen.WallpaperDetailScreen
import com.machado.unsplashwallpaper.presentation.wallpaper_list_screen.WallpaperListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnsplashWallpaperTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val viewModel: UnsplashViewModel = hiltViewModel()
                val snackBarHostState = remember { SnackbarHostState() }
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
                var showTopBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.WallpaperDetailScreen.route -> false // on this screen bottom bar should be hidden
                    else -> true // in all other cases show bottom bar
                }
                showTopBar = when (navBackStackEntry?.destination?.route) {
                    Screens.WallpaperDetailScreen.route -> false
                    Screens.FavoritesScreen.route -> false
                    Screens.WallpaperSearchScreen.route -> false
                    else -> true
                }
                val orderBy =
                    remember { mutableStateOf(UnsplashViewModel.ImageListOrder.POPULAR) }
                Scaffold(
                    topBar = {
                        if (showTopBar) Menu(viewModel = viewModel) {
                            viewModel.resetImageState()
                            orderBy.value = it
                        }
                    },
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screens.WallpaperListScreen.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screens.WallpaperListScreen.route) {
                            WallpaperListScreen(
                                navController = navController,
                                viewModel = viewModel,
                                orderBy = orderBy.value
                            )
                        }
                        composable(Screens.WallpaperSearchScreen.route) {
                            WallpaperSearchScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable(Screens.FavoritesScreen.route) {
                            FavoritesScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable(Screens.WallpaperDetailScreen.route) {
                            WallpaperDetailScreen(
                                viewModel = viewModel
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
    val screens: List<Screens> =
        listOf(Screens.WallpaperListScreen, Screens.WallpaperSearchScreen, Screens.FavoritesScreen)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(viewModel: UnsplashViewModel, orderBy: (UnsplashViewModel.ImageListOrder) -> Unit) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    // Creating a Top bar
    TopAppBar(
        title = { Text("Unsplash Wallpapers", color = Color.White) },
        actions = {
            // Creating Icon button for dropdown menu
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(
                    text = { Text(text = "Sort by latest") },
                    onClick = {
                        orderBy(UnsplashViewModel.ImageListOrder.LATEST)
                        viewModel.getImages(1, UnsplashViewModel.ImageListOrder.LATEST)
                    }
                )

                DropdownMenuItem(
                    text = { Text(text = "Sort by oldest") },
                    onClick = {
                        orderBy(UnsplashViewModel.ImageListOrder.OLDEST)
                        viewModel.getImages(1, UnsplashViewModel.ImageListOrder.OLDEST)
                    }
                )

                DropdownMenuItem(
                    text = { Text(text = "Sort by popular") },
                    onClick = {
                        orderBy(UnsplashViewModel.ImageListOrder.POPULAR)
                        viewModel.getImages(1, UnsplashViewModel.ImageListOrder.POPULAR)
                    }
                )
            }
        }
    )
}



