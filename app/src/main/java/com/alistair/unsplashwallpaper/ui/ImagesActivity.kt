package com.alistair.unsplashwallpaper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alistair.unsplashwallpaper.R
import com.alistair.unsplashwallpaper.api.RetrofitInstance
import com.alistair.unsplashwallpaper.databinding.ActivityImagesBinding
import com.alistair.unsplashwallpaper.db.ImageDatabase
import com.alistair.unsplashwallpaper.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding
lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageRepository = ImageRepository(ImageDatabase(this))
        val viewModelProviderFactory = ImageViewModelProviderFactory(imageRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ImageViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}