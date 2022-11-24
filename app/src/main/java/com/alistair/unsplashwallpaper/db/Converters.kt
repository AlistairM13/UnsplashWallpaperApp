package com.alistair.unsplashwallpaper.db

import androidx.room.TypeConverter
import com.alistair.unsplashwallpaper.models.Link
import com.alistair.unsplashwallpaper.models.Url

class Converters {

    @TypeConverter
    fun fromLink(link: Link): String{
        return link.download
    }

    @TypeConverter
    fun toLink(download: String): Link{
        return Link(download)
    }

    @TypeConverter
    fun fromUrl(url: Url): String{
        return url.regular
    }

    @TypeConverter
    fun toUrl(url:String) : Url{
        return Url(url)
    }

}