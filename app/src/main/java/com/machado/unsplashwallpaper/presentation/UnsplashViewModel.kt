package com.machado.unsplashwallpaper.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado.unsplashwallpaper.domain.model.ImageModel
import com.machado.unsplashwallpaper.domain.repository.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UnsplashViewModel @Inject constructor(
    val repository: UnsplashRepository,
    @Named("api_key") val apiKey: String
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var imageList by mutableStateOf<List<ImageModel>>(emptyList())
    var isSaved by mutableStateOf<Boolean>(false)

    // For one time events like showing snackBar
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

    init {
        getImages()
    }

    private fun getImages(page: Int = 1) = viewModelScope.launch {
        isLoading = true
        try {
            val response = repository.getImagesFromApi(apiKey, page)
            response.body()?.let { images ->
                isLoading = false
                imageList = images.map { it.toImageModel() }
            }
        } catch (e: HttpException) {
            isLoading = false
            e.printStackTrace()
            _eventFlow.emit(UIEvent.ShowSnackBar("Something went wrong"))
        } catch (e: IOException) {
            isLoading = false
            e.printStackTrace()
            _eventFlow.emit(UIEvent.ShowSnackBar("Check your internet connection"))
        }
    }

    fun saveImage(imageModel: ImageModel) = viewModelScope.launch {
        isSaved = if (isSaved) {
            repository.unSaveImage(imageModel.toImageEntity())
            false
        } else {
            repository.saveImage(imageModel.toImageEntity())
            true
        }
    }

    fun downloadImage(imageModel: ImageModel) {

    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}