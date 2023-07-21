package me.tbsten.gachagachazamurai.feature.settings.topImages

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.data.topImage.TopImageRepository
import okhttp3.internal.immutableListOf
import javax.inject.Inject

@HiltViewModel
class EditTopImagesViewModel @Inject constructor(
    private val topImageRepository: TopImageRepository,
) : ViewModel() {
    private val _images = MutableStateFlow(immutableListOf<Uri>())
    val images = _images.asStateFlow()

    init {
        refreshTopImages()
    }

    fun refreshTopImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _images.update { topImageRepository.getImages() }
        }
    }

    fun addTopImage(image: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            topImageRepository.saveImage(image)
        }.invokeOnCompletion { refreshTopImages() }
    }

    fun deleteTopImage(image: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            topImageRepository.deleteImage(image)
        }.invokeOnCompletion { refreshTopImages() }
    }
}
