package com.machado.unsplashwallpaper.util

interface Downloader {
    fun downloadFile(url: String, fileName: String): Long
}