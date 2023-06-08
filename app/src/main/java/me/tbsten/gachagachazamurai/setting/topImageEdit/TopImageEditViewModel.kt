package me.tbsten.gachagachazamurai.setting.topImageEdit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tbsten.gachagachazamurai.file.topScreenImage.TopScreenImageRepository
import javax.inject.Inject

@HiltViewModel
class TopImageEditViewModel @Inject constructor(
    private val topScreenImageRepository: TopScreenImageRepository,
) : ViewModel() {
    private val _images = MutableStateFlow<List<Uri>?>(null)
    val images = _images.asStateFlow()

    init {
        refreshImages()
    }

    fun refreshImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _images.update {
                topScreenImageRepository.getTopScreenImages()?.map { Uri.fromFile(it) }
            }
        }
    }

    fun addImages(images: List<Uri>) {
        _images.update {
            it?.plus(images)
        }
    }

    fun deleteImages(target: Uri) {
        _images.update {
            it?.filter { uri -> uri != target }
        }
    }

    fun saveImages() {
        val images = this.images.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            topScreenImageRepository.saveTopScreenImages(images)
            refreshImages()
        }
    }
}
