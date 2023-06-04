package com.machado.unsplashwallpaper.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado.unsplashwallpaper.data.db.entities.ImageEntity
import com.machado.unsplashwallpaper.domain.model.ImageModel
import com.machado.unsplashwallpaper.domain.repository.UnsplashRepository
import com.machado.unsplashwallpaper.util.AndroidDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 10

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

    var selectedImage = mutableStateOf<ImageModel?>(null)

    var imagesFromDb = mutableStateOf<List<ImageEntity>>(emptyList())


    init {
        getImages()
        viewModelScope.launch {
            repository.getSavedImages().collectLatest {
                imagesFromDb.value = it
            }
        }
    }

    fun getImages(page: Int = 1, orderBy: ImageListOrder = ImageListOrder.POPULAR) =
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.getImagesFromApi(apiKey, page, orderBy)
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
        val savedImage = repository.getImageById(imageModel.id)
        isSaved = if (savedImage != null) {
            repository.unSaveImage(savedImage)
            false
        } else {
            repository.saveImage(imageModel.toImageEntity())
            true
        }
    }

    fun downloadImage(imageModel: ImageModel, context: Context) {
        val downloader = AndroidDownloader(context)
        downloader.downloadFile(imageModel.downloadUrl, imageModel.id)
    }

    fun setSelectedImage(imageModel: ImageModel) = viewModelScope.launch {
        selectedImage.value = imageModel
        isSaved = repository.getImageById(imageModel.id) != null
    }

    val page = mutableStateOf(1)
    private var imageScrollPosition = 0

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeScrollPosition(position: Int) {
        imageScrollPosition = position
    }

    private fun appendImages(images: List<ImageModel>) {
        val current = ArrayList(this.imageList)
        current.addAll(images)
        this.imageList = current
    }

    fun nextPage(orderBy: ImageListOrder = ImageListOrder.POPULAR) = viewModelScope.launch {
        if ((imageScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            isLoading = true
            incrementPage()
            if (page.value > 1) {
                try {
                    val response = repository.getImagesFromApi(apiKey, page.value, orderBy)
                    response.body()?.let { images ->
                        appendImages(images.map { it.toImageModel() })
                        isLoading = false
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
        }
    }

    fun resetImageState() {
        page.value = 1
        onChangeScrollPosition(0)
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }

    enum class ImageListOrder(val orderByString: String) {
        LATEST("latest"),
        OLDEST("oldest"),
        POPULAR("popular"),
    }
}