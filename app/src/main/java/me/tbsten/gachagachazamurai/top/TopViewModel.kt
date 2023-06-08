package me.tbsten.gachagachazamurai.top

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.tbsten.gachagachazamurai.file.topScreenImage.TopScreenImageRepository
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val topScreenImageRepository: TopScreenImageRepository,
) : ViewModel() {
    private val _images = MutableStateFlow<List<Uri>?>(null)
    val images = _images.asStateFlow()

    init {
        _images.update {
            topScreenImageRepository.getTopScreenImages()?.map { Uri.fromFile(it) }
        }
    }
}